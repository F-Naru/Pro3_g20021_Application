package hcu.info.pro3_g20021_application;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FelicaReaderActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private static final String TAG = "FelicaReaderActivity";
    private static final byte NFC_F_CMD_RWOE = 0x06;
    private static final String SERVICE_CODE = "1A8B"; // 学生証のサービスコード
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felica_reader);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFCはサポートされていません。", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFCを有効にしてください。", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, FelicaReaderActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tagDetected};
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_F | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableReaderMode(this);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        NfcF nfcF = NfcF.get(tag);
        if (nfcF == null) {
            Log.e(TAG, "NfcF is null");
            return;
        }

        try {
            nfcF.connect();
            byte[] command = createRwoeCommand(tag.getId(), SERVICE_CODE, 4);
            byte[] response = nfcF.transceive(command);

            if (response != null) {
                // タグ情報の出力
                logTagInfo(tag);
                logResponseInfo(response);

                // 学籍番号のパース
                String studentId = parseStudentIdFromResponse(response);
                Log.d(TAG, "学籍番号: " + studentId);
                runOnUiThread(() -> {
                    Intent resultIntent = new Intent();
                    if (studentId != null) {
                        resultIntent.putExtra("studentId", studentId);
                        setResult(RESULT_OK, resultIntent);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                });
            } else {
                Log.e(TAG, "Response is null");
                runOnUiThread(() -> Toast.makeText(this, "FeliCa通信異常", Toast.LENGTH_SHORT).show());
            }
        } catch (IOException e) {
            Log.e(TAG, "FeliCa通信失敗: " + e.toString());
            runOnUiThread(() -> Toast.makeText(this, "FeliCa通信失敗", Toast.LENGTH_SHORT).show());
        } finally {
            try {
                nfcF.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing NfcF: " + e.toString());
            }
        }
    }

    private byte[] createRwoeCommand(byte[] idm, String serviceCode, int blockNum) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            bout.write(0); // data length
            bout.write(NFC_F_CMD_RWOE); // command code
            bout.write(idm); // IDm

            // サービスコードの処理
            bout.write(new byte[]{
                    (byte) Integer.parseInt(serviceCode.substring(2, 4), 16),
                    (byte) Integer.parseInt(serviceCode.substring(0, 2), 16)
            });

            bout.write(blockNum); // block size

            // block element
            for (int block = 0; block < blockNum; ++block) {
                bout.write(0x80);
                bout.write(block); // block number
            }

            byte[] rwoeRequest = bout.toByteArray();
            rwoeRequest[0] = (byte) rwoeRequest.length;
            return rwoeRequest;
        } catch (IOException e) {
            Log.e(TAG, "Error creating RWOE command: " + e.toString());
            return null;
        }
    }

    private String parseStudentIdFromResponse(byte[] response) {
        // 学籍番号の開始オフセットと長さを指定 (例: 0x1A88 から始まるデータ)
        final int START_OFFSET = 0x1A80; // 偽のオフセット
        final int STUDENT_ID_LENGTH = 16; // 学籍番号の長さ (適切に調整)

        if (response.length > START_OFFSET + STUDENT_ID_LENGTH) {
            byte[] idBytes = new byte[STUDENT_ID_LENGTH];
            System.arraycopy(response, START_OFFSET, idBytes, 0, STUDENT_ID_LENGTH);
            try {
                return new String(idBytes, "CP932").trim(); // CP932 エンコーディング
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Unsupported Encoding: " + e.toString());
            }
        }
        return null;
    }

    private void logTagInfo(Tag tag) {
        String idm = bytesToString(tag.getId());
        String systemCode = bytesToString(NfcF.get(tag).getSystemCode());
        String manufacturer = bytesToString(NfcF.get(tag).getManufacturer());

        Log.d(TAG, "IDm: " + idm);
        Log.d(TAG, "System Code: " + systemCode);
        Log.d(TAG, "Manufacturer: " + manufacturer);
    }

    private void logResponseInfo(byte[] response) {
        Log.d(TAG, "Response Length: " + response.length);
        Log.d(TAG, "Response: " + bytesToString(response));
    }

    private String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim();
    }
}

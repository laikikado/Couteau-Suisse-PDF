package bean;

import static com.itextpdf.forms.xfdf.XfdfConstants.DEST;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.IExternalDigest;
import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SignerPdf {
    
    public static void main(String[] args) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchAlgorithmException, CertificateException, CertificateException, CertificateException, UnrecoverableKeyException, CertificateException, GeneralSecurityException, CertificateException, CertificateException {
        File file = new File(DEST);
        file.mkdirs();

        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(KEYSTORE), PASSWORD);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
        Certificate[] chain = ks.getCertificateChain(alias);

        bean.SignerPdf app = new bean.SignerPdf();
        app.sign(SRC, DEST + RESULT_FILES[0], chain, pk, DigestAlgorithms.SHA256, provider.getName(),
                PdfSigner.CryptoStandard.CMS, "Signature1", "Test");
        app.sign(SRC, DEST + RESULT_FILES[1], chain, pk, DigestAlgorithms.SHA512, provider.getName(),
                PdfSigner.CryptoStandard.CMS, "Signature2", "Test");
        app.sign(SRC, DEST + RESULT_FILES[2], chain, pk, DigestAlgorithms.SHA256, provider.getName(),
                PdfSigner.CryptoStandard.CADES, "Signature3", "Test");
        app.sign(SRC, DEST + RESULT_FILES[3], chain, pk, DigestAlgorithms.RIPEMD160, provider.getName(),
                PdfSigner.CryptoStandard.CADES, "Signature4", "Test");
    }
    
    public void sign(String src, String dest, Certificate[] chain, PrivateKey pk, String digestAlgorithm,
            String provider, PdfSigner.CryptoStandard signatureType, String reason, String location)
            throws GeneralSecurityException, IOException {
        PdfReader reader = new PdfReader(src);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), new StampingProperties());

        Rectangle rect = new Rectangle(30, 650, 200, 100);
        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
        appearance
                .setReason(reason)
                .setLocation(location)
                .setReuseAppearance(false)
                .setPageRect(rect)
                .setPageNumber(1);
        signer.setFieldName("sig");

        IExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        IExternalDigest digest = new BouncyCastleDigest();

        signer.signDetached(digest, pks, chain, null, null, null, 0, signatureType);
    }
    
}

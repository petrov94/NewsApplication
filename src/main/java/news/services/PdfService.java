package news.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfWriter;
import news.controllers.ArticleController;
import news.models.Article;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Petar on 8/15/2017.
 */
public class PdfService {

    private static final String RESULT ="allarticles.pdf";

    public static void createPdf(ArrayList<Article> allArticles) throws DocumentException, IOException {
        File fontFile = new File("arialuni.ttf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        writer.getAcroForm().setNeedAppearances(true);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
        BaseFont unicode = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(unicode, 18);
        Font text = new Font(unicode, 10);
        for(Article art :allArticles) {
            Article article = ArticleController.getArticleText(art);
            addContent(document,article.getTitle(),font);
            addContent(document,article.getPublishedAt(),font);
            addContent(document,article.getDescription(),text);
            if(article.getUrlToImage()!=null) {
                document.add(getImage(article.getUrlToImage(), document));
            }
            addContent(document,article.getText(),text);
        }
        document.close();
    }
    private static void addContent(Document document,String paragraph, Font font) throws DocumentException {
            Paragraph p = new Paragraph(paragraph,font);
            document.add(p);
    }
    private static Image getImage(String imageUrl,Document document) throws IOException, BadElementException {
        URL url = new URL(imageUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        InputStream in = new BufferedInputStream(con.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        Image artImage = Image.getInstance(response);
        int indentation = 0;
        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - indentation) / artImage.getWidth()) * 100;
        artImage.scalePercent(scaler);
        return artImage;
    }
}

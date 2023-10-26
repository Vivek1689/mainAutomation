package utils.pdfUtil;

import java.io.IOException;

import de.redsix.pdfcompare.CompareResultImpl;
import de.redsix.pdfcompare.PdfComparator;

public class PDFComparator {


    public static void main(String[] args) throws IOException {
        String f1 = "/Users/shadabsayeed/IdeaProjects/GenericFrameworkNew/GenericAutomationFramework/generic-api/src/main/resources/PDFTestData/actual.pdf";
        String f2 = "/Users/shadabsayeed/IdeaProjects/GenericFrameworkNew/GenericAutomationFramework/generic-api/src/main/resources/PDFTestData/expected.pdf";
        String resFile = "/Users/shadabsayeed/IdeaProjects/GenericFrameworkNew/GenericAutomationFramework/generic-api/src/main/resources/PDFTestData/ResultData/pdfresult";
        String confFile = "/Users/shadabsayeed/IdeaProjects/GenericFrameworkNew/GenericAutomationFramework/generic-api/src/main/resources/PDFTestData/ignore.conf";
//        CompareResultImpl res = new PdfComparator(f1, f2).withIgnore(confFile).compare();
//        System.out.print(res.isEqual());
//        res.writeTo(resFile);

        System.out.printf(comparePDFStoreDiff(f1, f2, resFile, confFile)+"");


    }


    public static boolean comparePDFStoreDiff(String actualPDF, String expectedPDF, String resFile, String confFile) {

            CompareResultImpl res = null;
        if (confFile != null) {
            try {
                res = new PdfComparator(actualPDF, expectedPDF).withIgnore(confFile).compare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            res.writeTo(resFile);
            return res.isEqual();

        } else {
            try {
                res = new PdfComparator(actualPDF, expectedPDF).compare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            res.writeTo(resFile);
            return res.isEqual();

        }


    }


}

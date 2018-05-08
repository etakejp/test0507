package pdftest;

import java.io.ByteArrayOutputStream;
import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;




public class ModY  extends PDFTextStripper {
	

	static List<String> lines = new ArrayList<String>();
	
	public ModY() throws IOException {
    }
	
	   /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    @Override
    protected void writeString(String str, List<TextPosition> textPositions) throws IOException {
        lines.add(str);
        // you may process the line here itself, as and when it is obtained
    }
 
	
    public static void readContent( String str_file_name) throws IOException    {
    	
    	if (!str_file_name.matches(".*\\.pdf")) {
			System.err.println("is not be handled:" + str_file_name);
			return;
		}
    	
    	System.out.println(str_file_name);
    	
    	lines.clear();
    	
        PDDocument document = null;
        PrintWriter pw = null;
        //String fileName = "C:/Labo20/NTTêº/â¡çH/HTMLâª/W17-00671.pdf";
        try {
            document = PDDocument.load( new File(str_file_name) );
            PDFTextStripper stripper = new ModY();
            stripper.setSortByPosition( true );
            stripper.setStartPage( 0 );
            stripper.setEndPage( document.getNumberOfPages() );
 
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);
            
            // new file
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(str_file_name.replaceAll("\\.pdf", "_frompdf.txt"), false), "UTF-8")));
            
            // print lines
            for(String line:lines){
            	//System.out.println(line);
                pw.println(line);                
            }
            pw.flush();
            pw.close();
        }
        finally {
        	
            if( document != null ) {
                document.close();
            }
        }
    }

	public static void main(String[] args) {
		
		//if(args.length>0){
			//File pdfFile = new File(args[0]);
			//File resultFile = new File(args[1]);
			
			String str_dir_root ="C:/Labo20/NTTêº/â¡çH/pdf_from_doc";
			//File resultFile = new File("C:/Labo20/NTTêº/â¡çH/HTMLâª/W17-00671_test.txt");
			
			
			try{
				//new ExtractText(pdfFile,resultFile).process();
				
				File dir = new File(str_dir_root);

				File[] files_pptx = dir.listFiles();

				for (int i = 0; i < files_pptx.length; i++) {

					readContent(files_pptx[i].getAbsolutePath());

				}
				
				//new ModY(str_file_name,resultFile).process();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		//}
	}
	



}

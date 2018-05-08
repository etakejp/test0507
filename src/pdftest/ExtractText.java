package pdftest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class ExtractText {
	
	//private File pdfFile;
	//private File resultFile;
	
	private String str_pdf_file_name;
	private File resultFile;
	
	public ExtractText(String pdfFile,File resultFile){
		this.str_pdf_file_name=pdfFile;
		this.resultFile=resultFile;
	}
	
	public void process() throws IOException,FileNotFoundException {
		
		
		
		PrintWriter pw =new PrintWriter(new OutputStreamWriter(new FileOutputStream(resultFile)));
		
		
		PDFParser pp = new PDFParser(new RandomAccessBuffer(new FileInputStream(str_pdf_file_name)));
		
		
		
		
		pp.parse();
		PDDocument doc = pp.getPDDocument();
		int pageCount = doc.getNumberOfPages();
		//int pageCount = doc.getPageCount();
		
		System.out.println("page:" + pageCount);
		
		for(int i=0; i<pageCount; i++){
			
			int pageIndex = i+1;

			boolean force = true;
			boolean sort = true;
			boolean separateBeads = false;
			int startPage = pageIndex;
			int endPage = pageIndex;
			
			pw.println("--- "+pageIndex+" ---");
			
			MyPDFTextStripper stripper = new MyPDFTextStripper(pageIndex,pw);

			//stripper.setForceParsing( force );
			stripper.setSortByPosition( sort );
			stripper.setShouldSeparateByBeads( separateBeads );
			stripper.setStartPage( startPage );
			stripper.setEndPage( endPage );

			//StringWriter sw=new StringWriter();
			//PrintWriter output=new PrintWriter(sw);
			//stripper.writeText( doc, output );
			
			stripper.writeText( doc, pw );
			pw.close();
			//output.close();
			// sw.toString();
		}
	}
	
	class MyPDFTextStripper extends PDFTextStripper {
		
		private int pageIndex;
		private PrintWriter pw;
		public MyPDFTextStripper(int pageIndex,PrintWriter pw) throws IOException{
			//super("UTF-8");
			this.pageIndex=pageIndex;
			this.pw=pw;
		}
		protected void processTextPosition( TextPosition text ){
			super.processTextPosition(text);
			
			StringBuffer sb =new StringBuffer();
			sb.append(text.getUnicode() + ",");
			
			sb.append("pageIndex="+pageIndex).append(",");
			
			sb.append("x="+text.getX()).append(",");
			sb.append("y="+text.getY()).append(",");
			sb.append("width="+text.getWidth()).append(",");
			sb.append("height="+text.getHeight());
			
			
			
			pw.println( sb.toString() );
			
		}
	}

	public static void main(String[] args) {
		
		//if(args.length>0){
			//File pdfFile = new File(args[0]);
			//File resultFile = new File(args[1]);
			
			String str_file_name ="C:/Labo20/NTTêº/â¡çH/HTMLâª/W17-00671.pdf";
			File resultFile = new File("C:/Labo20/NTTêº/â¡çH/HTMLâª/W17-00671_test2.txt");
			
			
			try{
				//new ExtractText(pdfFile,resultFile).process();
				
				new ExtractText(str_file_name,resultFile).process();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		//}
	}


}

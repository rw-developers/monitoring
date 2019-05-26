package monitoring.elements;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.include.Alert;
import uk.ac.ox.cs.fdr.Assertion;
import uk.ac.ox.cs.fdr.FileLoadError;
import uk.ac.ox.cs.fdr.InputFileError;
import uk.ac.ox.cs.fdr.Session;
import uk.ac.ox.cs.fdr.fdr;

public class VerificationFDR {

	   private static ArrayList<String> Allchannel =new ArrayList<>();

	    public static String getAllchannelS() {
	        return AllchannelS;
	    }

	    public static void setAllchannelS(String allchannelS) {
	        AllchannelS = allchannelS;
	    }

	    private static String AllchannelS="";
	    
	    public static boolean[] testFDR(String chemin){
	        boolean bool[]=new boolean[2];
	        try {
	            Session session = new Session();
	            session.loadFile(chemin);
	            int i=-1;
	            for (Assertion assertion : session.assertions()) {
	                i++;
	                assertion.execute(null);
	                bool[i]=assertion.passed();
	            }
	            return bool;
	        }
	        catch (InputFileError error) {
	            System.out.println(error);
	        }
	        catch (FileLoadError error) {
	            System.out.println(error);
	        }

	        fdr.libraryExit();

	        return new boolean[]{false};
	    }
	 public void valideCspComponent(Component componenet ){
	        File fichCSP=new File("testFDR.csp");
	        String Text ="";
	        String channels ="";
	        String AllFormulla ="";
	        String formullaConcat ="";
	        String GlobaleFormulla ="GlobFormulla = ";
	        String generatorAuto ="";

	        try{
	            // Csp GlobalFormComponenet = conf.getParent().getExpGlobale();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	            for (int i = 0; i<componenet.getPorts().size();i++) {
	                Port p = componenet.getPorts().get(i);
	                formullaConcat += componenet.getName() + "_" + p.getCspExpression().getName() + ",";
	                channels += "channel " + p.getCspExpression().getName() + " : {0} \n";
	                // si le port out ou in je fait des modif sur la formulla csp
	                String[] tabIn = p.getCspExpression().getExpression().split("->");

	                String modBoucle = componenet.getName() + "_" + tabIn[tabIn.length-1];//reste a verifier
	                String restTab ="";
	                Pattern portNamePattern = Pattern.compile("([a-zA-Z]+[0-9]*)\\!([a-zA-Z]+[0-9]*)");
	                for(int d = 0 ; d<tabIn.length-1;d++){
	                    Matcher portN=portNamePattern.matcher(tabIn[d]);
	                    if(portN.matches()){
	                        String portname = portN.group(1);
	                        restTab+=portname+"!0->";
	                    }
	                    else {
	                        restTab+=tabIn[d]+"->";
	                    }
	                }
	                restTab+=modBoucle;
	                p.setCspExpressionModify(new Csp(p.getCspExpression().getName(),restTab));
	                AllFormulla += componenet.getName() + "_" + p.getCspExpression().getName() + " = "+restTab+"\n";

	            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	            
	            // mettre le ||| entre les formulles (formulle golbale )
	            String [] TabNameForm= formullaConcat.split(",");
	            
	            if(TabNameForm.length==1) { GlobaleFormulla += TabNameForm[0];generatorAuto += TabNameForm[0];}
	            else {

	                GlobaleFormulla +=TabNameForm[0];
	                generatorAuto += TabNameForm[0];
	                for (int m = 1 ; m< TabNameForm.length; m++){
	                    GlobaleFormulla += "|||"+TabNameForm[m];
	                    generatorAuto += "|||"+TabNameForm[m];
	                }
	            }
	            
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	            
	            // test asq si la formule globale auto ou po
	            String GlobalFormComponenet ="";
	            String Assert ="";
	            ArrayList<String> list1 = new ArrayList<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            ArrayList<String> list3 = new ArrayList<>();
	            Alert.display("",formullaConcat );
	            if(componenet.getExpGlobale().getExpression()==" "){
	                System.out.println("hediiiiiiiiiiiiiiiiii "+generatorAuto);
	                componenet.setExpGlobale(new Csp("autoFormula",generatorAuto));
	                GlobalFormComponenet =  "autoFormula  = " +generatorAuto;
	                Assert = "assert GlobFormulla[T= autoFormula";
	            }
	            else{

	                String formuleglobaleUser = componenet.getExpGlobale().getExpression();
	                Pattern formulaflesh = Pattern.compile("([a-zA-Z]+[0-9]*[!][a-zA-Z]+[0-9]*)(?=[-]>)");
	                Pattern formulaExclam = Pattern.compile("([a-zA-Z]+[0-9]*)(?=!)");
	                Matcher formulafleshMatcher = formulaflesh.matcher(formuleglobaleUser);
	                while(formulafleshMatcher.find()){
	                    list1.add(formulafleshMatcher.group(1));
	                }
	                for(int s=0;s<list1.size();s++){
	                    Matcher formulaExclamhMatcher = formulaExclam.matcher(list1.get(s));
	                    while(formulaExclamhMatcher.find()) {
	                        list2.add(formulaExclamhMatcher.group(1));
	                    }
	                }
	                for (int k =0;k<list2.size();k++){
	                    list3.add(list2.get(k)+"!0");
	                }
	                for(int d =0; d<list1.size();d++){
	                    formuleglobaleUser = formuleglobaleUser.replaceAll(list1.get(d),list3.get(d));
	                }

	                GlobalFormComponenet = componenet.getExpGlobale().getName()+"="+formuleglobaleUser+"\n";
	                Assert = "assert GlobFormulla[T="+componenet.getExpGlobale().getName();
	            }
	            // remplir le fichier csp
	            Text = channels+"\n"+AllFormulla+"\n"+GlobalFormComponenet+"\n"+GlobaleFormulla+"\n\n"+Assert;
	            FileWriter ffw=new FileWriter(fichCSP);
	            ffw.write(Text);
	            ffw.flush();
	            if(testFDR("testFDR.csp")[0])  {
	              
	               
	                Alert.display("Test FDR", "successful!");
	            }
	            else {
	              
	               
	                Alert.display("Test FDR", "error!");
	            }

	        }
	        catch(Exception e1){
	            e1.printStackTrace();
	        }

	    }
	    public boolean Search(ArrayList<String> array, String s,int m){
	        boolean bool=false;
	        for (int i=m+1;i<array.size();i++){
	            if(array.get(i).equals(s)){
	                bool=true;
	            }
	        }
	        return bool;
	    }

}

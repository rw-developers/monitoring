package monitoring.elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import uk.ac.ox.cs.fdr.Assertion;
import uk.ac.ox.cs.fdr.FileLoadError;
import uk.ac.ox.cs.fdr.InputFileError;
import uk.ac.ox.cs.fdr.Session;
import uk.ac.ox.cs.fdr.fdr;

public class VerificationFDR {

	   private static ArrayList<String> Allchannel =new ArrayList<>();
	   public ArrayList<String> Allmethode =new ArrayList<>();

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
	    
	    public ArrayList<String> ValidateConfiguration(Configuration conf){
	        File fichCSP=new File("testFDR.csp");
	        String Text ="";
	        String channels ="";
	        String AllFormulla ="";
	        String AllFormullaSeq="";
	        String GlobaleFormulla ="GlobaleFormulla = ";
	        String GlobaleFormullaConf ="";
	        Csp formullaPort = null;
	        String formulla="";
	        String formullaConcat ="";
	        String formullaConcatConf ="";
	        String CspArc ="";
	        String CspArcConf ="";
	        String ComAction ="";
	        String ComActionConf ="";
	        String formulaConf;
	        String channelsConf="";
	        String modBoucleConf="";
	        String allformulaConf="";
	        String textConf = "";
	        String confName;
	        String globalFormulaSeq="";
	        String AllFormullaSeqConf="";
	        String globalFormulaSeqConf="";
	        ArrayList<String> arrayChannel = new ArrayList<>();
	    


	        // liste pour diffirencier les arcs
	        ArrayList<String> BindInOutimplementations = new ArrayList<>();
	        int Compteur=0;
	        try{
	            //pour chaque instance on recupere les csp des prots de son composant type
	            for(int i =0;i<conf.implementations.size();i++){
	            	
	            	// Alert.display("", "ITERATION "+i);
	                String nameComposant =conf.implementations.get(i).getComponentType().getName();
	               // Alert.display("", nameComposant);
	                
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	                
	                for (int j = 0;j<conf.implementations.get(i).getComponentType().getPorts().size();j++) {
	                	// Alert.display("", "IMPLEMENTATION "+i+"   port  "+j);
	                    Port p = conf.implementations.get(i).getComponentType().getPorts().get(j);
	                    // recuperer la formulle du port
	                    formullaPort = p.getCspExpression();
	                    // le nom de l'instance + le ports
	                    formulla = conf.implementations.get(i).getName()+"_"+formullaPort.getName();
	                    formulaConf=formullaPort.getName();
	                    // recuprer tt les channel
	                    channels += "channel " +  formulla + " : {0} \n";
	                    //recuperer tt le channel sans redandence pour tester la reconfig
	                    boolean channelExist = false;
	                    boolean channelExistAll = false;
	                    for(int s = 0 ;s<arrayChannel.size();s++ ){
	                        if(arrayChannel.get(s).equals(formulaConf))channelExist=true;
	                    }
	                    if(channelExist == false)
	                    {channelsConf += "channel " +  formulaConf + " : {0} \n";
	                        String chan = "channel " +  formulaConf + " : {0} \n";
	                        for(int g= 0 ; g<Allchannel.size();g++){
	                            if(Allchannel.get(g).equals(chan)) channelExistAll=true;
	                        }
	                        if(channelExistAll==false)
	                        {   AllchannelS+=chan;
	                            Allchannel.add(chan);}
	                    }
	                    arrayChannel.add(formulaConf);
	                    // recuperer tt les formulles csp
	                    // si le port out ou in je fait des modif sur la formulla csp
	                  //  Alert.display("", "csp port");
	                    String[] tabOut = p.getCspExpressionModify().getExpression().split("->");
	                    String[]split3 = tabOut[tabOut.length-1].split("_");
	                    String modBoucle = nameComposant+"_"+conf.implementations.get(i).getName() + "_" + split3[1];//reste a verifier
	                    modBoucleConf=conf.name.getValue()+"_"+modBoucle;
	                    String restTab ="";
	                    String restTabConf="";
	                    for (int d =0;d<tabOut.length-1;d++){
	                        restTab+=conf.implementations.get(i).getName() + "_"+tabOut[d]+"->";
	                        restTabConf+=tabOut[d]+"->";
	                    }
	                   // Alert.display("", "ALLFORMULA");
	                    AllFormulla += nameComposant+"_"+conf.implementations.get(i).getName() + "_" + p.getCspExpression().getName() + " = " + restTab+modBoucle+ "\n";

	                    allformulaConf += conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + p.getCspExpression().getName()+"="+restTabConf+modBoucleConf+"\n";
	                }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                    // modifer le nom des channels  de l'exp globale de l'instance
	                    ArrayList<String> list1= new ArrayList<>();
	                    ArrayList<String> list2= new ArrayList<>();
	                    ArrayList<String> list6= new ArrayList<>();
	                    ArrayList<String> list2Conf= new ArrayList<>();
	                    String formuleGlobalUser = conf.implementations.get(i).getComponentType().getExpGlobale().getExpression();
	                    String formuleGlobalUserConf = conf.implementations.get(i).getComponentType().getExpGlobale().getExpression();
	                    Pattern formulasplit1 = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3}|\\)|$)|([a-zA-Z]+[0-9]*[!][a-zA-Z]+[0-9]*)(?=[-]>)|([a-zA-Z]+[0-9]*[?][a-zA-Z]+[0-9]*)(?=[-]>)");
	                    Matcher formulasplitMatcher = formulasplit1.matcher(formuleGlobalUser);
	                    //ce test pour l autoformula psq il faut prendre la fin $
	                if(conf.implementations.get(i).getComponentType().getExpGlobale().getName()=="autoFormula"){
	                	// Alert.display("", "AUTO FORMULA");
	                    String newAutoFormula ="";
	                    Pattern formulasplit2 = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3}|$)");
	                    Matcher formulasplitMatcher2 = formulasplit2.matcher(formuleGlobalUser);
	                    while(formulasplitMatcher2.find()) {
	                        String[] split6 = formulasplitMatcher2.group(1).split("_");
	                        formuleGlobalUser=formuleGlobalUser.replaceAll(formulasplitMatcher2.group(1),nameComposant+"_"+conf.implementations.get(i).getName()+"_"+split6[1]);
	                        formuleGlobalUserConf=formuleGlobalUserConf.replaceAll(formulasplitMatcher2.group(1),conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName()+"_"+split6[1]);
	                    }
	                }
	                else {
	                	// Alert.display("", "USER FORMULA");
	                    while (formulasplitMatcher.find()) {
	                        if (formulasplitMatcher.group(1) != null) {
	                            String[] nameex = formulasplitMatcher.group(1).split("_");
	                            list1.add(formulasplitMatcher.group(1));
	                            list2.add(conf.implementations.get(i).getComponentType().getName() + "_" + conf.implementations.get(i).getName() + "_" + nameex[1]);
	                            list6.add(conf.name.getValue()+"_"+conf.implementations.get(i).getComponentType().getName() + "_" + conf.implementations.get(i).getName() + "_" + nameex[1]);
	                        } else if (formulasplitMatcher.group(2) != null) {
	                            String[] nameex = formulasplitMatcher.group(2).split("\\!");
	                            list1.add(formulasplitMatcher.group(2));
	                            list2.add(conf.implementations.get(i).getName() + "_" + nameex[0] + "!0");
	                            list6.add(nameex[0] + "!0");
	                        } else if (formulasplitMatcher.group(3) != null) {

	                            String[] nameex = formulasplitMatcher.group(3).split("\\?");
	                            list1.add(nameex[0] + "\\?" + nameex[1]);
	                            list2.add(conf.implementations.get(i).getName() + "_" + nameex[0] + "\\?" + nameex[1]);
	                            list6.add(nameex[0] + "\\?" + nameex[1]);

	                        }
	                    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                    for (int z = 0; z < list1.size(); z++) {
	                        formuleGlobalUser = formuleGlobalUser.replaceAll(list1.get(z), list2.get(z));
	                        formuleGlobalUserConf = formuleGlobalUserConf.replaceAll(list1.get(z), list6.get(z));         
	                        }
	                //////////////////////////////////////////////////////////////////////////////////////////////////////////////    
	                    //System.out.println("\n\n\n\n");

	                }

	                AllFormulla +=nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                        + "= "+formuleGlobalUser +"\n" ;
	                if(i<conf.implementations.size()-1) {
	                    AllFormullaSeq += nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName();
	                    AllFormullaSeq += ";";
	                   // AllFormullaSeqConf+=conf.name.getValue()+"_"+AllFormullaSeq;

	                }
	                else  {
	                    AllFormullaSeq += nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName();
	                   // AllFormullaSeqConf+=conf.name.getValue()+"_"+AllFormullaSeq;
	                }
	               // System.out.println(AllFormullaSeq);
	                allformulaConf+=conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                        + "= "+formuleGlobalUserConf +"\n" ;
	              //  System.out.println(conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                      //  + "= "+formuleGlobalUserConf +"\n");

	                // fin de modification
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                /// arcs des ports des implementations
	               // Alert.display("", "ARC BEGIN");
	                for (int s = 0 ;s< conf.implementations.get(i).getPorts().size();s++) {
	                    Port port = conf.implementations.get(i).getPorts().get(s);
	                    Addconnector(port,conf);
	                    
	                    for (int m = 0; m < port.getListArc2().size(); m++) {
	                        Connector arc = port.getListArc2().get(m);
	                       
	                        // modifier dans l exp de binding pour remplacer !x par !0
	                        // String default1="znn_p2!x->znn_p1?x->znn_p1!x->znn_p2?x->bind";
	                        String[] splitbind2 = arc.getCSpArc().getExpression().split("->");
	                        String concatBind = "";
	                        String concatBinConf="";
	                        Pattern arcNamePattern = Pattern.compile("(([a-zA-Z]+[0-9]*)[_]([a-zA-Z]+[0-9]*))[!]([a-zA-Z]+[0-9]*)");
	                        for(int d = 0 ; d<splitbind2.length-1;d++){
	                            Matcher arcNameMatcher=arcNamePattern.matcher(splitbind2[d]);
	                            if (arcNameMatcher.matches()) {
	                                String arcname = arcNameMatcher.group(1);
	                                concatBind += arcname + "!0->";
	                                String[] splitbind3 = arcNameMatcher.group(1).split("_");
	                                concatBinConf += splitbind3[1] + "!0->";
	                            } else {
	                                concatBind += splitbind2[d] + "->";
	                                String[] splitbind3 = splitbind2[d].split("_");
	                                concatBinConf += splitbind3[1] + "->";
	                            }
	                        }
	                        concatBind+=conf.implementations.get(i).getName()+"_"+splitbind2[splitbind2.length-1];
	                        concatBinConf+=conf.name.getValue()+"_"+conf.implementations.get(i).getName()+"_"+splitbind2[splitbind2.length-1];
	                        // fin remplacement
	                        // pour remplacer les ? et ! par des points (les actions com )
	                        ArrayList<String> list3= new ArrayList<>();
	                        ArrayList<String> list4= new ArrayList<>();
	                        String[] splitAction = concatBind.split("->");

	                        //String[] splitActionConf = concatBinConf.split("->");


	                        Pattern formulaComPatern = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=!|\\?)");
	                        String [] splitactCom=null;
	                        ArrayList<String> ComActionArray = new ArrayList<>();
	                        ArrayList<String> ComActionConfArray = new ArrayList<>();
	                        for (int z = 0; z < splitAction.length -2; z++) {
	                            Matcher formulaComMatcher = formulaComPatern.matcher(splitAction[z]);
	                            while (formulaComMatcher.find()) {
	                                ComActionArray.add(formulaComMatcher.group(1) + ".0");
	                                splitactCom = formulaComMatcher.group(1).split("_");
	                                ComActionConfArray.add(splitactCom[1] + ".0");

	                            }
	                        }
	                        Matcher formulaComMatcher = formulaComPatern.matcher(splitAction[splitAction.length-2]);
	                        while(formulaComMatcher.find()) {
	                            ComActionArray.add(formulaComMatcher.group(1) + ".0");
	                            splitactCom = formulaComMatcher.group(1).split("_");
	                            ComActionConfArray.add(splitactCom[1] + ".0");
	                        }

	                        for (int z=0;z<ComActionArray.size()-1;z++){
	                            if(Search(ComActionArray,ComActionArray.get(z),z)==false)ComAction+=ComActionArray.get(z)+",";
	                            if(Search(ComActionConfArray,ComActionConfArray.get(z),z)==false) ComActionConf+=ComActionConfArray.get(z)+",";
	                        }
	                        ComAction+=ComActionArray.get(ComActionArray.size()-1);
	                        ComActionConf+=ComActionConfArray.get(ComActionConfArray.size()-1);


	                        /// fin remplacement


	                        // tester avant si l'arc est deja presenté
	                        ComponentImplementation arcIn = (ComponentImplementation) arc.getInPort().getParent();
	                        ComponentImplementation arcOut = (ComponentImplementation) arc.getOutPort().getParent();
	                        String arcInOut = arcIn.getName()+"_"+arc.getInPort().getNom() + "." + arcOut.getName()+"_"+arc.getOutPort().getNom();
	                        String arcOutIn = arcOut.getName()+"_"+arc.getOutPort().getNom() + "." + arcIn.getName()+"_"+arc.getInPort().getNom();
	                        boolean notexistBind = true;
	                        for (int c = 0; c < BindInOutimplementations.size(); c++) {
	                            if (BindInOutimplementations.get(c).equals(arcInOut) || BindInOutimplementations.get(c).equals(arcOutIn)) {
	                                notexistBind = false;
	                            }
	                        }
	                        if(notexistBind){
	                            BindInOutimplementations.add(arcInOut);
	                            BindInOutimplementations.add(arcOutIn);
	                            AllFormulla += conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "=" + concatBind + "\n";
	                            allformulaConf+=conf.name.getValue()+"_"+conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName()  + "=" + concatBinConf+ "\n";

	                            formullaConcat += "formula" +Compteur+ ",";
	                            String ExpInstance2 ="";
	                            String expInstance2Conf ="";
	                      
	                          
	                           // Alert.display("", arc.getInPort().name.getValue()+"h1");
	                          //  Alert.display("", port.name.getValue()+"h2");
	                         
                                  String khmissa2 ="";
	                            if(arc.getInPort().getNom().equals(port.getNom()))
	                            {     khmissa2 = arcOut.getComponentType().expMethod;
	                                ExpInstance2 =arcOut.getComponentType().getName()+"_"+arcOut.getName()+"_"+arcOut.getComponentType().getExpGlobale().getName();
	                                expInstance2Conf=conf.name.getValue()+"_"+arcOut.getComponentType().getName()+"_"+arcOut.getName()+"_"+arcOut.getComponentType().getExpGlobale().getName();

	                            }
	                            else
	                            {   khmissa2 = arcOut.getComponentType().expMethod;
	                                ExpInstance2 =arcIn.getComponentType().getName()+"_"+arcIn.getName()+"_"+arcIn.getComponentType().getExpGlobale().getName();
	                                expInstance2Conf=conf.name.getValue()+"_"+arcIn.getComponentType().getName()+"_"+arcIn.getName()+"_"+arcIn.getComponentType().getExpGlobale().getName();
	                            }
	                            
	                           // Alert.display("","arc compture");
	                            CspArc += "formula" +Compteur+ "="
	                                    + "(" + nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName() + "|||"
	                                    +ExpInstance2+")[|{" + ComAction + "}|]" + conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "\n";
	                            
	                            String khmissa = "("+conf.implementations.get(i).getComponentType().expMethod+")@("+khmissa2+")";
	                            Allmethode.add(khmissa);
	                            

	                            CspArcConf+=conf.name.getValue()+"_"+"formula" +Compteur+ "="
	                                    + "(" +conf.name.getValue()+"_"+ nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName() + "|||"
	                                    +expInstance2Conf+")[|{" + ComActionConf + "}|]" + conf.name.getValue()+"_"+conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "\n";
	                            Compteur++;

	                        }
	                        // mettre le vide dedans pour recharger les nvx actions coms
	                        ComAction = "";
	                        ComActionConf="";
	                    }
	                }
	                
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	              //  AllFormulla +="\n\n\n\n";
	               // allformulaConf+="\n\n\n\n";



	            }
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            globalFormulaSeq="sequentiel"+"="+AllFormullaSeq;
	            //globalFormulaSeqConf=""+"="+AllFormullaSeqConf;
	            //GlobaleFormullaConf
	            // mettre le ||| entre les formulles (formulle golbale )
	            String [] TabNameForm= formullaConcat.split(",");
	            if(TabNameForm.length==1){
	                GlobaleFormulla += TabNameForm[0];
	                GlobaleFormullaConf+=conf.name.getValue()+"_"+TabNameForm[0];
	            }
	            else {
	                GlobaleFormulla +=TabNameForm[0];
	                GlobaleFormullaConf+=conf.name.getValue()+"_"+TabNameForm[0];
	                for (int m = 1 ; m< TabNameForm.length; m++){
	                    GlobaleFormulla += "|||"+TabNameForm[m];
	                    GlobaleFormullaConf+="|||"+conf.name.getValue()+"_"+TabNameForm[m];
	                }
	            }

	            String Assert = "assert  GlobaleFormulla :[deadlock free [F]]";
	            String assertSeq= "assert  sequentiel :[deadlock free [F]]";
	            String AssertConf ="assert "+conf.name.getValue()+"_GlobaleFormulla :[deadlock free [F]]";
	            // pour le raffinement par trace
	            // assert Spec[T= Impl

	            Text = channels+"\n"+AllFormulla+"\n"+CspArc+"\n\n"+GlobaleFormulla+"\n\n"+Assert+"\n\n"+globalFormulaSeq+"\n\n"+assertSeq;
	            textConf= allformulaConf+"\n"+CspArcConf+ conf.name.getValue()+"_GlobaleFormulla  = "+GlobaleFormullaConf+"\n\n";
	            //affecter le textConfig a la config;
	            conf.TextConfig=textConf;

	            //System.out.println(textConf);
	            //Text = "channel \n ccc \n";
	            FileWriter ffw=new FileWriter(fichCSP);
	            ffw.write(Text);

	           // System.out.println("rahoo yekteb fel fichier");
	            ffw.flush();
	            boolean[] result=testFDR("testFDR.csp");

	            if(result[0])  {
	            
	              //  Alert.display("Test FDR", " parallel: successful !  ");
	                
	            }
	            else {
	            
	                
	              //  Alert.display("Test FDR", " executing configuration intances in parallel can't be done   ");
	            }
	            if(result[1])  {
	           
	             //   Alert.display("Test FDR", " sequential: successful!  ");
	            }
	            else {
	              
	               // Alert.display("Test FDR", "executing configuration intances in sequence can't be done ");
	            }


	        }
	        catch(Exception e1){
	            e1.printStackTrace();
	        }
			return Allmethode;

	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public String reconfig(Reconfiguration r)throws IOException {
	        //reconf=null;
	    	String res ="";
	     ArrayList<Reconfiguration>reconfig=new ArrayList<>();
	        String FinalText ="";
	        reconfig= new ArrayList<>();
	       

	        File fichCSP=new File("testFDR.csp");
	        // pour ecrire et effacer le contenu de fichier il faut ecrire le "vide " dedans
	        PrintWriter printwriter = new PrintWriter(new FileWriter(fichCSP));
	        printwriter.println(VerificationFDR.getAllchannelS()+"\n\n\n\n");

	        HashSet<Configuration> confs=new HashSet<>();
	        HashSet<Configuration> allConfs=new HashSet<>();



	      

	        //write declarations
	        printwriter.println(r.getConfSource().TextConfig);
	        printwriter.println(r.getConfDestination().TextConfig);
	        //write assertions
	        
	       
	        printwriter.println("assert "+r.getConfDestination().getName()+"_GlobaleFormulla"+"[T="+r.getConfSource().getName()+"_GlobaleFormulla"+"\n\n");     
	 
	        printwriter.flush();
	        printwriter.close();

	        //start checking
	        boolean[] result=testFDR("testFDR.csp");
	        if(result[0]) {
	        	//Alert.display("Reconfiguration ", "Reconfiguration Valid");
	        res ="Valid Reconfiguration [ "+r.getConfDestination().getName()+" Extend "+r.getConfSource().getName()+" ]"; }else {
	        	//Alert.display("Reconfiguration ", "Reconfiguration InValid");
	        res = "Invalid Reconfiguration [ "+r.getConfDestination().getName()+" Extend "+r.getConfSource().getName()+" ]";}
	       
	    
	    return res;
	    
	    
	    
	    
	    
	    
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
	               // System.out.println(restTab);
	                p.setCspExpressionModify(new Csp(p.getCspExpression().getName(),restTab));
	                AllFormulla += componenet.getName() + "_" + p.getCspExpression().getName() + " = "+restTab+"\n";
	               // System.out.println(AllFormulla);

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
	           // Alert.display("",formullaConcat );
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
	              //  Alert.display("Test FDR", "successful!");
	            }
	            else {
	              
	               // Alert.display("Test FDR", "error!");
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
	    public void Addconnector(Port p,Configuration c) {
	    	ArrayList<Connector> listArc = new ArrayList<>();
	    	ArrayList<Connector> tmp = (ArrayList<Connector>) c.getConnectors();
	    	for(int i=0;i<tmp.size();i++) {
	    		
	    	if(tmp.get(i).inPort.equals(p)||tmp.get(i).outPort.equals(p)) {
	    		p.listArc.add(tmp.get(i));
	    		p.listArc2.add(tmp.get(i));
	    	}	
	    		
	    		
	    		
	    		
	    		
	    	}
	    	
	    }
	    
	    public String valideCspComponent2(Component componenet ){
	        File fichCSP=new File("testFDR.csp");
	        String result = null ;
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
	          
	            if(componenet.getExpGlobale().getExpression()==" "){
	              //  System.out.println("hediiiiiiiiiiiiiiiiii "+generatorAuto);
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
	               // Alert.display("Test FDR", "successful!");
	                result = "Valid , Test FDR successful!";
	            }
	            else {
	              
	               // Alert.display("Test FDR", "error!");
	                result = "Invalide ,Test FDR error!";
	            }

	        }
	        catch(Exception e1){
	            e1.printStackTrace();
	        }

	        return result;
	    }
	  
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public ArrayList<String>  ValidateConfiguration2(Configuration conf){
	    	ArrayList<String> resultatl = new ArrayList<String>();
	    String	resultat = "";
	        File fichCSP=new File("testFDR.csp");
	        String Text ="";
	        String channels ="";
	        String AllFormulla ="";
	        String AllFormullaSeq="";
	        String GlobaleFormulla ="GlobaleFormulla = ";
	        String GlobaleFormullaConf ="";
	        Csp formullaPort = null;
	        String formulla="";
	        String formullaConcat ="";
	        String formullaConcatConf ="";
	        String CspArc ="";
	        String CspArcConf ="";
	        String ComAction ="";
	        String ComActionConf ="";
	        String formulaConf;
	        String channelsConf="";
	        String modBoucleConf="";
	        String allformulaConf="";
	        String textConf = "";
	        String confName;
	        String globalFormulaSeq="";
	        String AllFormullaSeqConf="";
	        String globalFormulaSeqConf="";
	        ArrayList<String> arrayChannel = new ArrayList<>();
	    


	        // liste pour diffirencier les arcs
	        ArrayList<String> BindInOutimplementations = new ArrayList<>();
	        int Compteur=0;
	        try{
	            //pour chaque instance on recupere les csp des prots de son composant type
	            for(int i =0;i<conf.implementations.size();i++){
	            	
	            	// Alert.display("", "ITERATION "+i);
	                String nameComposant =conf.implementations.get(i).getComponentType().getName();
	               // Alert.display("", nameComposant);
	                
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	                
	                for (int j = 0;j<conf.implementations.get(i).getComponentType().getPorts().size();j++) {
	                	// Alert.display("", "IMPLEMENTATION "+i+"   port  "+j);
	                    Port p = conf.implementations.get(i).getComponentType().getPorts().get(j);
	                    // recuperer la formulle du port
	                    formullaPort = p.getCspExpression();
	                    // le nom de l'instance + le ports
	                    formulla = conf.implementations.get(i).getName()+"_"+formullaPort.getName();
	                    formulaConf=formullaPort.getName();
	                    // recuprer tt les channel
	                    channels += "channel " +  formulla + " : {0} \n";
	                    //recuperer tt le channel sans redandence pour tester la reconfig
	                    boolean channelExist = false;
	                    boolean channelExistAll = false;
	                    for(int s = 0 ;s<arrayChannel.size();s++ ){
	                        if(arrayChannel.get(s).equals(formulaConf))channelExist=true;
	                    }
	                    if(channelExist == false)
	                    {channelsConf += "channel " +  formulaConf + " : {0} \n";
	                        String chan = "channel " +  formulaConf + " : {0} \n";
	                        for(int g= 0 ; g<Allchannel.size();g++){
	                            if(Allchannel.get(g).equals(chan)) channelExistAll=true;
	                        }
	                        if(channelExistAll==false)
	                        {   AllchannelS+=chan;
	                            Allchannel.add(chan);}
	                    }
	                    arrayChannel.add(formulaConf);
	                    // recuperer tt les formulles csp
	                    // si le port out ou in je fait des modif sur la formulla csp
	                  //  Alert.display("", "csp port");
	                    String[] tabOut = p.getCspExpressionModify().getExpression().split("->");
	                    String[]split3 = tabOut[tabOut.length-1].split("_");
	                    String modBoucle = nameComposant+"_"+conf.implementations.get(i).getName() + "_" + split3[1];//reste a verifier
	                    modBoucleConf=conf.name.getValue()+"_"+modBoucle;
	                    String restTab ="";
	                    String restTabConf="";
	                    for (int d =0;d<tabOut.length-1;d++){
	                        restTab+=conf.implementations.get(i).getName() + "_"+tabOut[d]+"->";
	                        restTabConf+=tabOut[d]+"->";
	                    }
	                   // Alert.display("", "ALLFORMULA");
	                    AllFormulla += nameComposant+"_"+conf.implementations.get(i).getName() + "_" + p.getCspExpression().getName() + " = " + restTab+modBoucle+ "\n";

	                    allformulaConf += conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + p.getCspExpression().getName()+"="+restTabConf+modBoucleConf+"\n";
	                }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                    // modifer le nom des channels  de l'exp globale de l'instance
	                    ArrayList<String> list1= new ArrayList<>();
	                    ArrayList<String> list2= new ArrayList<>();
	                    ArrayList<String> list6= new ArrayList<>();
	                    ArrayList<String> list2Conf= new ArrayList<>();
	                    String formuleGlobalUser = conf.implementations.get(i).getComponentType().getExpGlobale().getExpression();
	                    String formuleGlobalUserConf = conf.implementations.get(i).getComponentType().getExpGlobale().getExpression();
	                    Pattern formulasplit1 = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3}|\\)|$)|([a-zA-Z]+[0-9]*[!][a-zA-Z]+[0-9]*)(?=[-]>)|([a-zA-Z]+[0-9]*[?][a-zA-Z]+[0-9]*)(?=[-]>)");
	                    Matcher formulasplitMatcher = formulasplit1.matcher(formuleGlobalUser);
	                    //ce test pour l autoformula psq il faut prendre la fin $
	                if(conf.implementations.get(i).getComponentType().getExpGlobale().getName()=="autoFormula"){
	                	// Alert.display("", "AUTO FORMULA");
	                    String newAutoFormula ="";
	                    Pattern formulasplit2 = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=\\|~\\||\\[\\]|;|[|]{3}|$)");
	                    Matcher formulasplitMatcher2 = formulasplit2.matcher(formuleGlobalUser);
	                    while(formulasplitMatcher2.find()) {
	                        String[] split6 = formulasplitMatcher2.group(1).split("_");
	                        formuleGlobalUser=formuleGlobalUser.replaceAll(formulasplitMatcher2.group(1),nameComposant+"_"+conf.implementations.get(i).getName()+"_"+split6[1]);
	                        formuleGlobalUserConf=formuleGlobalUserConf.replaceAll(formulasplitMatcher2.group(1),conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName()+"_"+split6[1]);
	                    }
	                }
	                else {
	                	// Alert.display("", "USER FORMULA");
	                    while (formulasplitMatcher.find()) {
	                        if (formulasplitMatcher.group(1) != null) {
	                            String[] nameex = formulasplitMatcher.group(1).split("_");
	                            list1.add(formulasplitMatcher.group(1));
	                            list2.add(conf.implementations.get(i).getComponentType().getName() + "_" + conf.implementations.get(i).getName() + "_" + nameex[1]);
	                            list6.add(conf.name.getValue()+"_"+conf.implementations.get(i).getComponentType().getName() + "_" + conf.implementations.get(i).getName() + "_" + nameex[1]);
	                        } else if (formulasplitMatcher.group(2) != null) {
	                            String[] nameex = formulasplitMatcher.group(2).split("\\!");
	                            list1.add(formulasplitMatcher.group(2));
	                            list2.add(conf.implementations.get(i).getName() + "_" + nameex[0] + "!0");
	                            list6.add(nameex[0] + "!0");
	                        } else if (formulasplitMatcher.group(3) != null) {

	                            String[] nameex = formulasplitMatcher.group(3).split("\\?");
	                            list1.add(nameex[0] + "\\?" + nameex[1]);
	                            list2.add(conf.implementations.get(i).getName() + "_" + nameex[0] + "\\?" + nameex[1]);
	                            list6.add(nameex[0] + "\\?" + nameex[1]);

	                        }
	                    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                    for (int z = 0; z < list1.size(); z++) {
	                        formuleGlobalUser = formuleGlobalUser.replaceAll(list1.get(z), list2.get(z));
	                        formuleGlobalUserConf = formuleGlobalUserConf.replaceAll(list1.get(z), list6.get(z));         
	                        }
	                //////////////////////////////////////////////////////////////////////////////////////////////////////////////    
	                    System.out.println("\n\n\n\n");

	                }

	                AllFormulla +=nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                        + "= "+formuleGlobalUser +"\n" ;
	                if(i<conf.implementations.size()-1) {
	                    AllFormullaSeq += nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName();
	                    AllFormullaSeq += ";";
	                   // AllFormullaSeqConf+=conf.name.getValue()+"_"+AllFormullaSeq;

	                }
	                else  {
	                    AllFormullaSeq += nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName();
	                   // AllFormullaSeqConf+=conf.name.getValue()+"_"+AllFormullaSeq;
	                }
	              //  System.out.println(AllFormullaSeq);
	                allformulaConf+=conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                        + "= "+formuleGlobalUserConf +"\n" ;
	              //  System.out.println(conf.name.getValue()+"_"+nameComposant+"_"+conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName()
	                  //      + "= "+formuleGlobalUserConf +"\n");

	                // fin de modification
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                /// arcs des ports des implementations
	               // Alert.display("", "ARC BEGIN");
	                for (int s = 0 ;s< conf.implementations.get(i).getPorts().size();s++) {
	                    Port port = conf.implementations.get(i).getPorts().get(s);
	                    Addconnector(port,conf);
	                    
	                    for (int m = 0; m < port.getListArc2().size(); m++) {
	                        Connector arc = port.getListArc2().get(m);
	                       
	                        // modifier dans l exp de binding pour remplacer !x par !0
	                        // String default1="znn_p2!x->znn_p1?x->znn_p1!x->znn_p2?x->bind";
	                        String[] splitbind2 = arc.getCSpArc().getExpression().split("->");
	                        String concatBind = "";
	                        String concatBinConf="";
	                        Pattern arcNamePattern = Pattern.compile("(([a-zA-Z]+[0-9]*)[_]([a-zA-Z]+[0-9]*))[!]([a-zA-Z]+[0-9]*)");
	                        for(int d = 0 ; d<splitbind2.length-1;d++){
	                            Matcher arcNameMatcher=arcNamePattern.matcher(splitbind2[d]);
	                            if (arcNameMatcher.matches()) {
	                                String arcname = arcNameMatcher.group(1);
	                                concatBind += arcname + "!0->";
	                                String[] splitbind3 = arcNameMatcher.group(1).split("_");
	                                concatBinConf += splitbind3[1] + "!0->";
	                            } else {
	                                concatBind += splitbind2[d] + "->";
	                                String[] splitbind3 = splitbind2[d].split("_");
	                                concatBinConf += splitbind3[1] + "->";
	                            }
	                        }
	                        concatBind+=conf.implementations.get(i).getName()+"_"+splitbind2[splitbind2.length-1];
	                        concatBinConf+=conf.name.getValue()+"_"+conf.implementations.get(i).getName()+"_"+splitbind2[splitbind2.length-1];
	                        // fin remplacement
	                        // pour remplacer les ? et ! par des points (les actions com )
	                        ArrayList<String> list3= new ArrayList<>();
	                        ArrayList<String> list4= new ArrayList<>();
	                        String[] splitAction = concatBind.split("->");

	                        //String[] splitActionConf = concatBinConf.split("->");


	                        Pattern formulaComPatern = Pattern.compile("([a-zA-Z]+[0-9]*[_][a-zA-Z]+[0-9]*)(?=!|\\?)");
	                        String [] splitactCom=null;
	                        ArrayList<String> ComActionArray = new ArrayList<>();
	                        ArrayList<String> ComActionConfArray = new ArrayList<>();
	                        for (int z = 0; z < splitAction.length -2; z++) {
	                            Matcher formulaComMatcher = formulaComPatern.matcher(splitAction[z]);
	                            while (formulaComMatcher.find()) {
	                                ComActionArray.add(formulaComMatcher.group(1) + ".0");
	                                splitactCom = formulaComMatcher.group(1).split("_");
	                                ComActionConfArray.add(splitactCom[1] + ".0");

	                            }
	                        }
	                        Matcher formulaComMatcher = formulaComPatern.matcher(splitAction[splitAction.length-2]);
	                        while(formulaComMatcher.find()) {
	                            ComActionArray.add(formulaComMatcher.group(1) + ".0");
	                            splitactCom = formulaComMatcher.group(1).split("_");
	                            ComActionConfArray.add(splitactCom[1] + ".0");
	                        }

	                        for (int z=0;z<ComActionArray.size()-1;z++){
	                            if(Search(ComActionArray,ComActionArray.get(z),z)==false)ComAction+=ComActionArray.get(z)+",";
	                            if(Search(ComActionConfArray,ComActionConfArray.get(z),z)==false) ComActionConf+=ComActionConfArray.get(z)+",";
	                        }
	                        ComAction+=ComActionArray.get(ComActionArray.size()-1);
	                        ComActionConf+=ComActionConfArray.get(ComActionConfArray.size()-1);


	                        /// fin remplacement


	                        // tester avant si l'arc est deja presenté
	                        ComponentImplementation arcIn = (ComponentImplementation) arc.getInPort().getParent();
	                        ComponentImplementation arcOut = (ComponentImplementation) arc.getOutPort().getParent();
	                        String arcInOut = arcIn.getName()+"_"+arc.getInPort().getNom() + "." + arcOut.getName()+"_"+arc.getOutPort().getNom();
	                        String arcOutIn = arcOut.getName()+"_"+arc.getOutPort().getNom() + "." + arcIn.getName()+"_"+arc.getInPort().getNom();
	                        boolean notexistBind = true;
	                        for (int c = 0; c < BindInOutimplementations.size(); c++) {
	                            if (BindInOutimplementations.get(c).equals(arcInOut) || BindInOutimplementations.get(c).equals(arcOutIn)) {
	                                notexistBind = false;
	                            }
	                        }
	                        if(notexistBind){
	                            BindInOutimplementations.add(arcInOut);
	                            BindInOutimplementations.add(arcOutIn);
	                            AllFormulla += conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "=" + concatBind + "\n";
	                            allformulaConf+=conf.name.getValue()+"_"+conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName()  + "=" + concatBinConf+ "\n";

	                            formullaConcat += "formula" +Compteur+ ",";
	                            String ExpInstance2 ="";
	                            String expInstance2Conf ="";
	                      
	                          
	                          //  Alert.display("", arc.getInPort().name.getValue()+"h1");
	                          //  Alert.display("", port.name.getValue()+"h2");
	                         

	                            if(arc.getInPort().getNom().equals(port.getNom()))
	                            {
	                                ExpInstance2 =arcOut.getComponentType().getName()+"_"+arcOut.getName()+"_"+arcOut.getComponentType().getExpGlobale().getName();
	                                expInstance2Conf=conf.name.getValue()+"_"+arcOut.getComponentType().getName()+"_"+arcOut.getName()+"_"+arcOut.getComponentType().getExpGlobale().getName();

	                            }
	                            else
	                            {
	                                ExpInstance2 =arcIn.getComponentType().getName()+"_"+arcIn.getName()+"_"+arcIn.getComponentType().getExpGlobale().getName();
	                                expInstance2Conf=conf.name.getValue()+"_"+arcIn.getComponentType().getName()+"_"+arcIn.getName()+"_"+arcIn.getComponentType().getExpGlobale().getName();
	                            }
	                            
	                           // Alert.display("","arc compture");
	                            CspArc += "formula" +Compteur+ "="
	                                    + "(" + nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName() + "|||"
	                                    +ExpInstance2+")[|{" + ComAction + "}|]" + conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "\n";

	                            CspArcConf+=conf.name.getValue()+"_"+"formula" +Compteur+ "="
	                                    + "(" +conf.name.getValue()+"_"+ nameComposant + "_" + conf.implementations.get(i).getName() + "_" + conf.implementations.get(i).getComponentType().getExpGlobale().getName() + "|||"
	                                    +expInstance2Conf+")[|{" + ComActionConf + "}|]" + conf.name.getValue()+"_"+conf.implementations.get(i).getName() + "_" + arc.getCSpArc().getName() + "\n";
	                            Compteur++;

	                        }
	                        // mettre le vide dedans pour recharger les nvx actions coms
	                        ComAction = "";
	                        ComActionConf="";
	                    }
	                }
	                
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	                AllFormulla +="\n\n\n\n";
	                allformulaConf+="\n\n\n\n";



	            }
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            globalFormulaSeq="sequentiel"+"="+AllFormullaSeq;
	            //globalFormulaSeqConf=""+"="+AllFormullaSeqConf;
	            //GlobaleFormullaConf
	            // mettre le ||| entre les formulles (formulle golbale )
	            String [] TabNameForm= formullaConcat.split(",");
	            if(TabNameForm.length==1){
	                GlobaleFormulla += TabNameForm[0];
	                GlobaleFormullaConf+=conf.name.getValue()+"_"+TabNameForm[0];
	            }
	            else {
	                GlobaleFormulla +=TabNameForm[0];
	                GlobaleFormullaConf+=conf.name.getValue()+"_"+TabNameForm[0];
	                for (int m = 1 ; m< TabNameForm.length; m++){
	                    GlobaleFormulla += "|||"+TabNameForm[m];
	                    GlobaleFormullaConf+="|||"+conf.name.getValue()+"_"+TabNameForm[m];
	                }
	            }

	            String Assert = "assert  GlobaleFormulla :[deadlock free [F]]";
	            String assertSeq= "assert  sequentiel :[deadlock free [F]]";
	            String AssertConf ="assert "+conf.name.getValue()+"_GlobaleFormulla :[deadlock free [F]]";
	            // pour le raffinement par trace
	            // assert Spec[T= Impl

	            Text = channels+"\n"+AllFormulla+"\n"+CspArc+"\n\n"+GlobaleFormulla+"\n\n"+Assert+"\n\n"+globalFormulaSeq+"\n\n"+assertSeq;
	            textConf= allformulaConf+"\n"+CspArcConf+ conf.name.getValue()+"_GlobaleFormulla  = "+GlobaleFormullaConf+"\n\n";
	            //affecter le textConfig a la config;
	            conf.TextConfig=textConf;

	            //System.out.println(textConf);
	            //Text = "channel \n ccc \n";
	            FileWriter ffw=new FileWriter(fichCSP);
	            ffw.write(Text);

	            System.out.println("rahoo yekteb fel fichier");
	            ffw.flush();
	            boolean[] result=testFDR("testFDR.csp");

	            if(result[0])  {
	            
	              //  Alert.display("Test FDR", " parallel: successful !  ");
	                resultat = "Valide Test FDR  parallel: successful !  ";
	                resultatl.add(resultat);
	                
	            }
	            else {
	            
	                
	               // Alert.display("Test FDR", " executing configuration intances in parallel can't be done   ");
	           resultat = "Invalide Test FDR  executing configuration intances in parallel can't be done   ";
	           resultatl.add(resultat);
	            }
	            if(result[1])  {
	           
	               // Alert.display("Test FDR", " sequential: successful!  ");
	                resultat = "Valide Test FDR sequential: successful!  ";
	                resultatl.add(resultat);
	            }
	            else {
	              
	               // Alert.display("Test FDR", "executing configuration intances in sequence can't be done ");
	                resultat = "Invalide Test FDR executing configuration intances in sequence can't be done ";
	                resultatl.add(resultat);
	            }


	        }
	        catch(Exception e1){
	            e1.printStackTrace();
	        }
return resultatl;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

}

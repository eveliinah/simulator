package simulaattorinrunko5FX.src.eduni.distributions;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Calendar;
import javax.swing.JOptionPane;

/** A class which allows for automatically parsing a distribution implementation and generate
 * a class inheriting from Generator accordingly. Use the return type to implements either
 * DiscreteGenerator or ContinuousGenerator. Prompt the user to specify constraints on the parameters.
 * @date 4/10/2002
 * @author F.Mallet
 */
public class MetaGenerator {
    String [] params;
    String methodName, className;
    int nbC; // number of constraints
    public MetaGenerator(String name, int nb) { 
	methodName = name;
	className = name.substring(0,1).toUpperCase()+name.substring(1);
	nbC = nb;
    }
    public void generate() {	
	try {
	    String fileName = "eduni/distributions/"+className+".java";
	    PrintWriter pw = new PrintWriter(new FileWriter(fileName));
	    Distributions d = new Distributions();
	    Class<? extends Distributions> cl = d.getClass();
	    java.lang.reflect.Method [] methods = cl.getDeclaredMethods();
	    for (int i=0; i<methods.length; i++) {
		if (methods[i].getName().equals(methodName)) {
		    generate(pw, methods[i]);
		    break;
		}
	    }
	    pw.close();
	    System.out.println("File "+fileName+" successfully generated.");
	    System.exit(0);
	} catch (java.io.IOException ioe) {
	    System.err.println(ioe);
	}
    }
    public boolean generate(PrintWriter pw, java.lang.reflect.Method m) {
	pw.println("package eduni.distributions;\n");
	pw.print("/** A random number generator based on the ");
        pw.print(methodName); pw.println(" distribution.");
	pw.println(" * Automatically generated by <code>MetaGenerator</code>.");
	pw.println(" * @version 1.0, "+Calendar.getInstance().getTime());
	pw.println(" * @author F.Mallet, adaptation from C.Simatos's original");
	pw.println(" */\n");
	pw.print("public class "+className+" extends Generator implements ");
	if (m.getReturnType().getName().equals("double"))
	    pw.print("Continuous");
	else pw.print("Discrete");
	pw.println("Generator {");
	StringBuffer param = new StringBuffer(""), 
	    paramT = new StringBuffer("");
	@SuppressWarnings("rawtypes")
	Class []ps = m.getParameterTypes();
	params = new String[ps.length];
	for (int i=0; i<ps.length; i++) {
	    params[i] = JOptionPane.showInputDialog("Name of the parameter "+i, "p"+i);
	    pw.println("    private "+ps[i].getName()+" "+params[i]+";");
	    paramT.append(ps[i].getName()); paramT.append(" ");
	    paramT.append(params[i]); param.append(params[i]);
	    if (i+1<ps.length) { paramT.append(", "); param.append(", "); }
	}
	pw.println("    /**");
	pw.println("     * the seed is aumatically provided by the <code>SeedGenerator</code>");
	pw.println("     */");
	pw.println("    public "+className+"("+paramT+") {");
	pw.println("        super();");
	pw.println("        set("+param+");");
	pw.println("    }\n");
	pw.println("    /**");
	pw.println("     * The constructor with which a specific seed is set for the random");
	pw.println("     * number generator");
	pw.println("     * @param seed The initial seed for the generator, two instances with");
	pw.println("     *             the same seed will generate the same sequence of numbers");
	pw.println("     */");
	pw.println("    public "+className+"("+paramT+", long seed) {");
	pw.println("        super(seed);");
	pw.println("        set("+param+");");
	pw.println("    }\n");
	pw.println("    private void set("+paramT+") {");
	for (int i=0; i<nbC; i++) {
	    String c = JOptionPane.showInputDialog("Enter constraint violation "+i+"/"+nbC,
						   "a<=0");
	    String err = JOptionPane.showInputDialog("Error message when "+c);
	    pw.println("        if ("+c+")");
	    pw.println("            throw new ParameterException(\""+className+": "+err+".\");");
	}
	for (int i=0; i<params.length; i++)
	    pw.println("        this."+params[i]+" = "+params[i]+";");
	pw.println("    }\n");
	pw.println("    /**");
	pw.println("     * Generate a new random number.");
	pw.println("     * @return The next random number in the sequence");
	pw.println("     */");
	pw.println("    public "+m.getReturnType().getName()+" sample() { ");
	pw.println("         return distrib."+methodName+"("+param+");");
	pw.println("    }\n}");
	return true;
    }

    static public void main(String args[]) {
	if (args.length==0) {
	    System.err.println("Usage: java MetaGenerator <distrib_name> [#<nb_constraints>]");
	    System.exit(1);
	}
	int nb = 0;
	try {
	    if (args.length>1) nb = Integer.parseInt(args[1]);
	} catch (NumberFormatException nfe) {
	    nb = 0;
	}
	new MetaGenerator(args[0], nb).generate();
    }
}

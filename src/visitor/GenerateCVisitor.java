package visitor;

import java.util.StringJoiner;

import syntaxTree.Args;
import syntaxTree.Body;
import syntaxTree.CompStat;
import syntaxTree.Decls;
import syntaxTree.Expr;
import syntaxTree.ParDecls;
import syntaxTree.Programma;
import syntaxTree.Stat;
import syntaxTree.Statements;
import syntaxTree.VarDecls;
import syntaxTree.VarDeclsInit;
import syntaxTree.VarInitValue;
import syntaxTree.Vars;
import syntaxTree.arithOp.AddOp;
import syntaxTree.arithOp.DivOp;
import syntaxTree.arithOp.MultOp;
import syntaxTree.arithOp.SubOp;
import syntaxTree.arithOp.UminusOp;
import syntaxTree.comp.Leaf;
import syntaxTree.declsOp.DefDeclNoPar;
import syntaxTree.declsOp.DefDeclPar;
import syntaxTree.declsOp.VarDecl;
import syntaxTree.leaf.BoolConst;
import syntaxTree.leaf.CharConst;
import syntaxTree.leaf.DoubleConst;
import syntaxTree.leaf.IdConst;
import syntaxTree.leaf.IntConst;
import syntaxTree.leaf.ParTypeLeaf;
import syntaxTree.leaf.StringConst;
import syntaxTree.leaf.TypeLeaf;
import syntaxTree.logicOp.AndOp;
import syntaxTree.logicOp.NotOp;
import syntaxTree.logicOp.OrOp;
import syntaxTree.relOp.EqOp;
import syntaxTree.relOp.GeOp;
import syntaxTree.relOp.GtOp;
import syntaxTree.relOp.LeOp;
import syntaxTree.relOp.LtOp;
import syntaxTree.statOp.AssignOp;
import syntaxTree.statOp.CallOp;
import syntaxTree.statOp.IfThenElseOp;
import syntaxTree.statOp.IfThenOp;
import syntaxTree.statOp.ReadOp;
import syntaxTree.statOp.WhileOp;
import syntaxTree.statOp.WriteOp;
import syntaxTree.utils.ParDeclSon;
import syntaxTree.varDeclInitOp.VarInit;
import syntaxTree.varDeclInitOp.VarNotInit;
import syntaxTree.wrapper.DeclsWrapper;
import syntaxTree.wrapper.VarDeclsInitWrapper;

public class GenerateCVisitor implements Visitor<String> {

	public GenerateCVisitor() {
	}
	
	@Override
	public String visit(Args n) throws RuntimeException {
		String tr="";
		for(Expr e: n.getChildList()) {
			tr+=e.accept(this)+",";
		}
		return tr = tr.substring(0, tr.length()-1);
		
	}

	@Override
	public String visit(Body n) throws RuntimeException {
		String tr = n.getVd().accept(this)+"";
		tr += n.getS().accept(this);
		return tr;
	}

	@Override
	public String visit(CompStat n) throws RuntimeException {
		return n.getS().accept(this)+"";
	}

	@Override
	public String visit(Decls n) throws RuntimeException {
		String tr = "";
		for(DeclsWrapper dw: n.getChildList()) {
			tr+=dw.accept(this);
		}
		return tr+="\n";
	}

	@Override
	public String visit(DefDeclNoPar n) throws RuntimeException {
		String tr = "void";
		tr+= "void "+n.getId().accept(this)+"(){\n";
		tr+= n.getB().accept(this);
		return tr+="}\n";
	}

	@Override
	public String visit(DefDeclPar n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(ParDecls n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(Programma n) throws RuntimeException {
		String tr = "#include <stdio.h>\n"
				+ "#include <stdlib.h>\n"
				+ "#include<string.h>\n"
				+ "#include <unistd.h>\n"
				+ "\n"
				+ "typedef int bool;\n"
				+ "#define false 0\n"
				+ "#define true 1\n"
				+ "#define STRING_CONST 256\n"
				+ "\n"
				+ "typedef char string[STRING_CONST];\n"
				+ "string yasplBuffer;\n"
				+ "string toParse;\n"
				+ "\n";
		//visita Decls
		tr+= n.getD().accept(this);
		tr+="int main(void){\n";
		//visita Statements
		tr+= n.getS().accept(this);
		return tr+="return 0;\n}";
	}

	@Override
	public String visit(Statements n) throws RuntimeException {
		String tr="";
		for(Stat s: n.getChildList()) {
			tr += s.accept(this);
		}
		return tr;
	}

	@Override
	public String visit(VarDecl n) throws RuntimeException {
		String tr = "";
		tr+= n.getT().accept(this)+" ";
		tr+= n.getVdi().accept(this);
		return tr;
	}

	@Override
	public String visit(VarDecls n) throws RuntimeException {
		String tr = "";
		for(VarDecl vd: n.getChildList()) {
			tr += vd.accept(this);
		}
		return tr;
	}

	@Override
	public String visit(VarDeclsInit n) throws RuntimeException {
		String tr="";
		for(VarDeclsInitWrapper vdiw: n.getChildList()) {
			tr += vdiw.accept(this)+",";
		}
		tr = tr.substring(0, tr.length()-1);
		return tr+=";\n";
	}

	@Override
	public String visit(VarInitValue n) throws RuntimeException {
		return ""+n.getE().accept(this);
	}

	@Override
	public String visit(Vars n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	//TODO AGGIUSTARE
	public String visit(AddOp n) throws RuntimeException {
		return resultOfAddOp(n);
	}

	@Override
	public String visit(DivOp n) throws RuntimeException {
		return n.getE1().accept(this)+" / "+n.getE2().accept(this);
	}

	@Override
	public String visit(MultOp n) throws RuntimeException {
		return n.getE1().accept(this)+" * "+n.getE2().accept(this);
	}

	@Override
	public String visit(SubOp n) throws RuntimeException {
		return n.getE1().accept(this)+" - "+n.getE2().accept(this);
	}

	@Override
	public String visit(UminusOp n) throws RuntimeException {
		return "-"+n.getE().accept(this);
	}

	@Override
	public String visit(AndOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" && "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(NotOp n) throws RuntimeException {
		return "!("+n.getE().accept(this)+")";
	}

	@Override
	public String visit(OrOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" || "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(EqOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" == "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(GeOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" >= "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(GtOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" > "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(LeOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" <= "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(LtOp n) throws RuntimeException {
		return "("+n.getE1().accept(this)+" < "+n.getE2().accept(this)+")";
	}

	@Override
	public String visit(BoolConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(IdConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(IntConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(DoubleConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(CharConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(StringConst n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(AssignOp n) throws RuntimeException {
		final boolean typeOfE = n.getE().getType().toString().equalsIgnoreCase("string");
		return (typeOfE)? "strcpy("+n.getId().accept(this)+", "+n.getE().accept(this)+");\n"
						: n.getId().accept(this)+" = "+n.getE().accept(this)+";\n";
	}

	@Override
	public String visit(CallOp n) throws RuntimeException {
		return n.getId().accept(this)+"("+n.getA().accept(this)+");\n";
	}

	@Override
	public String visit(IfThenElseOp n) throws RuntimeException {
		String tr="if(";
		tr+= n.getE().accept(this)+"){\n";
		tr+= n.getCs1().accept(this)+"}\nelse{\n";
		tr+= n.getCs2().accept(this)+"}\n";
		return tr;
	}

	@Override
	public String visit(IfThenOp n) throws RuntimeException {
		String tr="if(";
		tr+= n.getE().accept(this)+"){\n";
		tr+= n.getCs().accept(this)+"}\n";
		return tr;
	}

	@Override
	public String visit(ReadOp n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(WhileOp n) throws RuntimeException {
		return "while("+n.getE().accept(this)+"){\n"
				+ n.getCs().accept(this)+"\n}\n";
	}

	@Override
	public String visit(WriteOp n) throws RuntimeException {
		String tr="";
		Expr e = n.getA().getChildList().get(0);
		String typeOfE = e.getType()+"";
		//System.out.println(e instanceof AddOp);
		if(!(e instanceof AddOp)) {
			tr+="printf(\""+escapeForC(typeOfE)+"\\n\","+e.accept(this)+");\n";
		}else {
			tr+="\n";
			tr+=e.accept(this)+"\n";
			tr+="printf(\"%s\\n\", yasplBuffer);\n";
			tr+="\n";
			//tr+="printf(\""+escapeForC(typeOfE)+"\","+e.accept(this)+");\n";
		}
		 
		return tr;
	}
	@Override
	public String visit(Leaf n) throws RuntimeException {		
		return n.getValue();
	}

	@Override
	public String visit(ParDeclSon n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(VarInit n) throws RuntimeException {
		String tr = "";
		tr += n.getId().accept(this)+" = ";
		tr += n.getViv().accept(this);
		return tr;
	}

	@Override
	public String visit(VarNotInit n) throws RuntimeException {
		return ""+n.getId().accept(this);
	}

	@Override
	public String visit(TypeLeaf n) throws RuntimeException {
		return n.getValue().toLowerCase();
	}

	@Override
	public String visit(ParTypeLeaf n) throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String escapeForC(String type) {
		String tr="";
		if(type.equalsIgnoreCase("string") || type.equalsIgnoreCase("bool")) {
			tr="%s";
		}else {
			if(type.equalsIgnoreCase("int")) {
				tr="%d";
			}else {
				if(type.equalsIgnoreCase("double")) {
					tr="%f";
				}
				else {
					if(type.equalsIgnoreCase("char")) {
						tr="%c";
					}
				}
			}
		}
		return tr;
	}
	
	private String resultOfAddOp(AddOp n) {
		String tr="";
		
		final boolean isE1String = n.getE1().getType().toString().equalsIgnoreCase("string");
		final boolean isE2String = n.getE2().getType().toString().equalsIgnoreCase("string");
		
		final boolean isE1Int = n.getE1().getType().toString().equalsIgnoreCase("int");
		final boolean isE2Int = n.getE2().getType().toString().equalsIgnoreCase("int");
		
		final boolean isE1Char = n.getE1().getType().toString().equalsIgnoreCase("char");
		final boolean isE2Char = n.getE2().getType().toString().equalsIgnoreCase("char");
		
		final boolean isE1Double = n.getE1().getType().toString().equalsIgnoreCase("double");
		final boolean isE2Double = n.getE2().getType().toString().equalsIgnoreCase("double");
		
		final boolean isE1Bool = n.getE1().getType().toString().equalsIgnoreCase("bool");
		final boolean isE2Bool = n.getE2().getType().toString().equalsIgnoreCase("bool");
		
		if(isE1String && isE2String){
				tr += "strcpy(yasplBuffer,"+n.getE1().accept(this)+");\n";
				tr += "strcat(yasplBuffer, "+n.getE2().accept(this)+");\n";
		}else {
			if(	  (isE1String && isE2Int) 
				||(isE1String && isE2Char)
				||(isE1String && isE2Double)
				||(isE1String && isE2Bool)
				){
				tr += addWhitOneString(n.getE1(), n.getE2(), isE2Bool);
				/*tr += "strcpy(yasplBuffer,"+n.getE1().accept(this)+");\n";
				if(!isE2Bool) {
					tr+="sprintf(toParse,\""+escapeForC(n.getE2().getType()+"")+"\", "+n.getE2().accept(this)+");\n";
				}else {
					String s = n.getE2().accept(this)+"";
					if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
						tr+="sprintf(toParse,\"%s\", \""+s+"\");\n";
					}else {
						tr+="sprintf(toParse,\"%s\", "+s+"? \"true\" : \"false\");\n";
					}
				}*/
				tr += "strcat(yasplBuffer, toParse);\n";
		}
		 else {
			 
			 if((isE2String && isE1Int) 
				||(isE2String && isE1Char)
				||(isE2String && isE1Double)
				||(isE2String && isE1Bool)
			) {
				 /*tr += "strcpy(yasplBuffer,"+n.getE2().accept(this)+");\n";
				 if(!isE1Bool) {
						tr+="sprintf(toParse,\""+escapeForC(n.getE1().getType()+"")+"\", "+n.getE1().accept(this)+");\n";
					}else {
						String s = n.getE1().accept(this)+"";
						if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
							tr+="sprintf(toParse,\"%s\", \""+s+"\");\n";
							
						}else {
							tr+="sprintf(toParse,\"%s\", "+s+"? \"true\" : \"false\");\n";
						}	
					}*/
				 tr += addWhitOneString(n.getE2(), n.getE1(), isE1Bool);
				 tr += "strcat(toParse, yasplBuffer);\n";
				 tr += "strcpy(yasplBuffer, toParse);\n;";
			 }else {
			 if(!(isE1String && isE2String)) {
					tr+=n.getE1().accept(this)+" + "+n.getE2().accept(this);
				}
			 }
		}
		}
		return tr;
	}
	
	private String addWhitOneString(Expr e1, Expr e2, boolean b) {
		String tr="";
		tr += "strcpy(yasplBuffer,"+e1.accept(this)+");\n";
		if(!b) {
			tr+="sprintf(toParse,\""+escapeForC(e2.getType()+"")+"\", "+e2.accept(this)+");\n";
		}else {
			String s = e2.accept(this)+"";
			if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
				tr+="sprintf(toParse,\"%s\", \""+s+"\");\n";
			}else {
				tr+="sprintf(toParse,\"%s\", "+s+"? \"true\" : \"false\");\n";
			}
		}
		return tr;
	}
	
}
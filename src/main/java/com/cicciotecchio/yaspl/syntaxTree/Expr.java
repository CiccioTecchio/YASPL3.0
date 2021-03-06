package com.cicciotecchio.yaspl.syntaxTree;

import java_cup.runtime.ComplexSymbolFactory.Location;
import com.cicciotecchio.yaspl.syntaxTree.components.Internal;
import com.cicciotecchio.yaspl.syntaxTree.components.Leaf;
import com.cicciotecchio.yaspl.syntaxTree.leaf.IdConst;
import com.cicciotecchio.yaspl.visitor.Visitor;

public abstract class Expr extends Internal {
	
	private String op;
	
	//Operazioni binarie
	public Expr(Location left, Location right, String op, Expr e1, Expr e2) {
		super(left, right, op, e1, e2);
	}
	
	//Operazioni unarie
	public Expr(Location left, Location right, String op, Expr e) {
		super(left, right, op, e);
	}
	
	//Increment and Decrement
	public Expr(Location left, Location right, String op, IdConst id) {
		super(left, right, op, id);
	}
	
	public Expr(Location left, Location right, String op, Leaf id) {
		super(left, right, op, id);
	}
	
	
	
	public abstract Object accept(Visitor<?> visitor);

	public String getOp() {
		return op;
	}

	
		

}

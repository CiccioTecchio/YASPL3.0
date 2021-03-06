package com.cicciotecchio.yaspl.syntaxTree;

import java_cup.runtime.ComplexSymbolFactory.Location;
import com.cicciotecchio.yaspl.syntaxTree.components.Internal;
import com.cicciotecchio.yaspl.visitor.Visitable;
import com.cicciotecchio.yaspl.visitor.Visitor;

public class CompStat extends Internal implements Visitable {

	private String op;
	private Statements s;
	
	public CompStat(Location left, Location right,String op, Statements s) {
		super(left, right, op, s);
		this.op = op;
		this.s = s;
	}

	@Override
	public Object accept(Visitor<?> visitor) {
		return visitor.visit(this);
	}

	public String getOp() {
		return op;
	}

	public Statements getS() {
		return s;
	}
}

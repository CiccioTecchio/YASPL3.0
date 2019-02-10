package syntaxTree.relOp;

import syntaxTree.Expr;
import visitor.Visitable;
import visitor.Visitor;

public class EqOp extends Expr implements Visitable {

	public EqOp(String op, Expr e1, Expr e2) {
		super(op, e1, e2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EqOp accept(Visitor<?> visitor) {
		// TODO Auto-generated method stub
		return (EqOp) visitor.visit(this);
	}

}
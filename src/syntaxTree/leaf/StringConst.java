package syntaxTree.leaf;

import syntaxTree.Expr;
import syntaxTree.components.Leaf;
import visitor.Visitable;
import visitor.Visitor;

public class StringConst extends Expr implements Visitable {
	
	private String op;
	private Leaf id;

	public StringConst(String op, Leaf id) {
		super(op, id);
		this.op = op;
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object accept(Visitor<?> visitor) {
		return visitor.visit(this);
	}

	public String getOp() {
		return op;
	}

	public Leaf getId() {
		return id;
	}
	
	

}

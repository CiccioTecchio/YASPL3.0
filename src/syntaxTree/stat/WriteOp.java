package syntaxTree.stat;

import syntaxTree.Args;
import syntaxTree.Stat;
import visitor.Visitable;
import visitor.Visitor;

public class WriteOp extends Stat implements Visitable {


	public WriteOp(String op, Args a) {
		super(op, a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WriteOp accept(Visitor<?> visitor) {
		return (WriteOp) visitor.visit(this);
	}



}

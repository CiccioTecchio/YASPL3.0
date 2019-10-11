package syntaxTree.statOp;

import syntaxTree.Stat;
import syntaxTree.leaf.IdConst;
import visitor.Visitable;
import visitor.Visitor;

public class PostFixDecrement extends Stat implements Visitable {

	private String op;
	private IdConst id;
	
	public PostFixDecrement(String op, IdConst id) {
	super(op, id);
	this.op = op;
	this.id = id;
	}
	
	@Override
	public Object accept(Visitor<?> visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public IdConst getId() {
		return id;
	}

	public void setId(IdConst id) {
		this.id = id;
	}

}

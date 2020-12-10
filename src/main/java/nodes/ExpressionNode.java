package nodes;

import java.util.Collections;
import java.util.List;

import intefaces.Visitable;
import intefaces.Visitor;


public abstract class ExpressionNode implements Visitable {

	public static class BinaryExpression extends ExpressionNode {

		public ExpressionNode leftExpression;
		public int operation;
		public ExpressionNode rightExpresion;

		public BinaryExpression(ExpressionNode leftExpression, int operation, ExpressionNode rightExpresion) {
			this.leftExpression = leftExpression;
			this.operation = operation;
			this.rightExpresion = rightExpresion;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class UnaryExpression extends ExpressionNode {

		public int operation;
		public ExpressionNode rightExpression;

		public UnaryExpression(int operation, ExpressionNode rightExpression) {
			this.operation = operation;
			this.rightExpression = rightExpression;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}

	}

	public static class IntegerConstant extends ExpressionNode {

		public int value;

		public IntegerConstant(int value) {
			this.value = value;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class FloatConstant extends ExpressionNode {

		public float value;

		public FloatConstant(float value) {
			this.value = value;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class StringConstant extends ExpressionNode {

		public String value;

		public StringConstant(String value) {
			this.value = value;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class BooleanConstant extends ExpressionNode {

		public boolean value;

		public BooleanConstant(boolean value) {
			this.value = value;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class NullConstant extends ExpressionNode {

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class IdentifierExpression extends ExpressionNode {

		public int xleft, xright;
		public String value;

		public IdentifierExpression(int xleft, String value, int xright) {
			this.xleft = xleft;
			this.value = value;
			this.xright = xright;
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

	public static class CallProcedureExpression extends ExpressionNode {

		public IdentifierExpression id;
		public List<ExpressionNode> expressionList;

		public CallProcedureExpression(IdentifierExpression id, List<ExpressionNode> expressionList) {
			this.id = id;
			this.expressionList = expressionList;
		}

		public CallProcedureExpression(IdentifierExpression id) {
			this.id = id;
			this.expressionList = Collections.emptyList();
		}

		@Override
		public Object accept(Visitor visitor) {
			return visitor.visit(this);
		}
	}

}

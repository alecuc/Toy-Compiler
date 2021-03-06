package parser.nodes;

import java.util.Collections;
import java.util.List;

import common.exceptions.SemanticException;
import common.interfaces.Visitable;
import common.interfaces.Visitor;
import java_cup.runtime.ComplexSymbolFactory.Location;
import parser.nodes.ExpressionNode.IdentifierExpression;

public abstract class StatementNode extends TypedNode implements Visitable {

	public StatementNode(Location left, Location right) {
		super(left, right);
	}

	public StatementNode(){
		super();
	}

	public static class ReadStatement extends StatementNode {

		public List<IdentifierExpression> idList;

		public ReadStatement(List<IdentifierExpression> idList) {
			super();
			this.idList = idList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);
		}
	}

	public static class WriteStatement extends StatementNode {

		public List<ExpressionNode> expressionList;

		public WriteStatement(List<ExpressionNode> expressionList) {
			super();
			this.expressionList = expressionList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);
		}
	}

	public static class AssignStatement extends StatementNode {

		public List<IdentifierExpression> idList;
		public List<ExpressionNode> expressionList;

		public AssignStatement(List<IdentifierExpression> idList, List<ExpressionNode> expressionList) {
			super();
			this.idList = idList;
			this.expressionList = expressionList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);
		}
	}

	public static class CallProcedureStatement extends StatementNode {

		public IdentifierExpression id;
		public List<ExpressionNode> expressionList;

		public CallProcedureStatement(IdentifierExpression id, List<ExpressionNode> expressionList,Location left, Location right) {
			super(left,right);
			this.id = id;
			this.expressionList = expressionList;
		}

		public CallProcedureStatement(IdentifierExpression id,Location left, Location right) {
			super(left,right);
			this.id = id;
			this.expressionList = Collections.emptyList();
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
		
			return visitor.visit(this);

		}
	}

	public static class WhileStatement extends StatementNode {

		public List<StatementNode> conditionStatementList;
		public ExpressionNode conditionExpression;
		public List<StatementNode> bodyStatementList;

		public WhileStatement(List<StatementNode> conditionStatementList, ExpressionNode conditionExpression,
				List<StatementNode> bodyStatementList) {
					super();
					this.conditionStatementList = conditionStatementList;
			this.conditionExpression = conditionExpression;
			this.bodyStatementList = bodyStatementList;
		}

		public WhileStatement(ExpressionNode conditionExpression, List<StatementNode> bodyStatementList) {
			super();
			this.conditionStatementList = Collections.emptyList();
			this.conditionExpression = conditionExpression;
			this.bodyStatementList = bodyStatementList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);

		}
	}

	public static class IfStatement extends StatementNode {

		public ExpressionNode conditionExpression;
		public List<ElifStatement> elifStatementList;
		public List<StatementNode> ifBodyStatatementList;
		public List<StatementNode> elseStatementList;

		public IfStatement(ExpressionNode conditionExpression, List<StatementNode> ifBodyStatatementList,
				List<ElifStatement> elifStatementList, List<StatementNode> elseStatementList) {
					super();
					this.conditionExpression = conditionExpression;
			this.elifStatementList = elifStatementList;
			this.ifBodyStatatementList = ifBodyStatatementList;
			this.elseStatementList = elseStatementList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);

		}

	}

	public static class ElifStatement extends StatementNode {

		public ExpressionNode expression;
		public List<StatementNode> elifBodyStatementList;

		public ElifStatement(ExpressionNode expression, List<StatementNode> elifBodyStatementList) {
			super();
			this.expression = expression;
			this.elifBodyStatementList = elifBodyStatementList;
		}

		@Override
		public Object accept(Visitor visitor) throws SemanticException {
			return visitor.visit(this);
		}
	}

}

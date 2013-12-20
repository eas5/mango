package cc.concurrent.mango.runtime.parser;


import cc.concurrent.mango.runtime.ParsedSql;
import cc.concurrent.mango.runtime.RuntimeContext;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ash
 */
public class ASTRootNode extends SimpleNode {

    public ASTRootNode(int i) {
        super(i);
    }

    public ASTRootNode(Parser p, int i) {
        super(p, i);
    }

    public ParsedSql getSqlAndArgs(RuntimeContext context) {
        StringBuffer sql = new StringBuffer();
        List<Object> args = Lists.newArrayList();

        for (int i = 0; i < jjtGetNumChildren(); i++) {
            Node node = jjtGetChild(i);
            if (node instanceof ASTText) {
                ASTText text = (ASTText) node;
                sql.append(text.getText());
            } else if (node instanceof ASTBlank) {
                ASTBlank text = (ASTBlank) node;
                sql.append(text.getBlank());
            } else if (node instanceof ASTOutParam) {
                ASTOutParam outParam = (ASTOutParam) node;
                args.add(outParam.value(context));
                sql.append("?");
            } else if (node instanceof ASTInParam) {
                ASTInParam outParam = (ASTInParam) node;
                List<Object> objs = outParam.values(context);
                args.addAll(objs);
                sql.append("in (?");
                for (int j = 1; j < objs.size(); j++) {
                    sql.append(",?");
                }
                sql.append(")");
            } else {
                sql.append(node.value(context));
            }
        }

        return new ParsedSql(sql.toString(), args.toArray());
    }

}

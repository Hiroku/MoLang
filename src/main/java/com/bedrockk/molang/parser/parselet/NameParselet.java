package com.bedrockk.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.ast.FuncCallExpression;
import com.bedrockk.molang.ast.NameExpression;
import com.bedrockk.molang.parser.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class NameParselet implements PrefixParselet {

    @Override
    public Expression parse(MoLangParser parser, Token token) {
        List<Expression> args = parser.parseArgs();
        String name = parser.fixNameShortcut(token.getText());
        ArrayList<String> names = new ArrayList(List.of(name.split("\\.")));

        Expression nameExpr = new NameExpression(names);

        String nameHead = parser.getNameHead(name);
        if (args.size() > 0 || nameHead.equals("query") || nameHead.equals("math") || nameHead.equals("script")){
            return new FuncCallExpression(nameExpr, args.toArray(new Expression[args.size()]));
        }

        return nameExpr;
    }

    /*

        @Override
    public Expression parse(MoLangParser parser, Token token) {
        List<Expression> args = parser.parseArgs();
        String name = parser.fixNameShortcut(token.getText());

        String category = parser.getNameHead(name);
        String variable = name.substring(name.indexOf(".") + 1);

        Expression nameExpr = new NameExpression(category, variable);

        if (args.size() > 0 || parser.getNameHead(name).equals("query") || parser.getNameHead(name).equals("math")){
            return new FuncCallExpression(nameExpr, args.toArray(new Expression[0]));
        }


        return nameExpr;
    }
     */
}

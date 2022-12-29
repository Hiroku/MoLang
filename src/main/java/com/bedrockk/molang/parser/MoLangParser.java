package com.bedrockk.molang.parser;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.parselet.*;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.parser.tokenizer.TokenIterator;
import com.bedrockk.molang.parser.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MoLangParser {

    public final static HashMap<String, PrefixParselet> prefixParselets = new HashMap<>();
    public final static HashMap<String, InfixParselet> infixParselets = new HashMap<>();

    private final TokenIterator tokenIterator;
    private final List<Token> readTokens = new ArrayList<>();

    static {
        prefixParselets.put(TokenType.NAME.symbol, new NameParselet());
        prefixParselets.put(TokenType.STRING.symbol, new StringParselet());
        prefixParselets.put(TokenType.NUMBER.symbol, new NumberParselet());
        prefixParselets.put(TokenType.TRUE.symbol, new BooleanParselet());
        prefixParselets.put(TokenType.FALSE.symbol, new BooleanParselet());
        prefixParselets.put(TokenType.RETURN.symbol, new ReturnParselet());
        prefixParselets.put(TokenType.CONTINUE.symbol, new ContinueParselet());
        prefixParselets.put(TokenType.BREAK.symbol, new BreakParselet());
        prefixParselets.put(TokenType.LOOP.symbol, new LoopParselet());
        prefixParselets.put(TokenType.FOR_EACH.symbol, new ForEachParselet());
        prefixParselets.put(TokenType.THIS.symbol, new ThisParselet());
        prefixParselets.put(TokenType.BRACKET_LEFT.symbol, new GroupParselet());
        prefixParselets.put(TokenType.CURLY_BRACKET_LEFT.symbol, new BracketScopeParselet());
        prefixParselets.put(TokenType.MINUS.symbol, new UnaryMinusParselet());
        prefixParselets.put(TokenType.PLUS.symbol, new UnaryPlusParselet());
        prefixParselets.put(TokenType.BANG.symbol, new BooleanNotParselet());

        infixParselets.put(TokenType.QUESTION.symbol, new TernaryParselet());
        infixParselets.put(TokenType.ARRAY_LEFT.symbol, new ArrayAccessParselet());
        infixParselets.put(TokenType.PLUS.symbol, new GenericBinaryOpParselet(Precedence.SUM));
        infixParselets.put(TokenType.MINUS.symbol, new GenericBinaryOpParselet(Precedence.SUM));
        infixParselets.put(TokenType.SLASH.symbol, new GenericBinaryOpParselet(Precedence.PRODUCT));
        infixParselets.put(TokenType.ASTERISK.symbol, new GenericBinaryOpParselet(Precedence.PRODUCT));
        infixParselets.put(TokenType.EQUALS.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.NOT_EQUALS.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.GREATER.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.GREATER_OR_EQUALS.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.SMALLER.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.SMALLER_OR_EQUALS.symbol, new GenericBinaryOpParselet(Precedence.COMPARE));
        infixParselets.put(TokenType.AND.symbol, new GenericBinaryOpParselet(Precedence.AND));
        infixParselets.put(TokenType.OR.symbol, new GenericBinaryOpParselet(Precedence.OR));
        infixParselets.put(TokenType.COALESCE.symbol, new GenericBinaryOpParselet(Precedence.COALESCE));
        infixParselets.put(TokenType.ARROW.symbol, new GenericBinaryOpParselet(Precedence.ARROW));
        infixParselets.put(TokenType.ASSIGN.symbol, new AssignParselet());
    }

    public MoLangParser(TokenIterator iterator) {
        this.tokenIterator = iterator;
    }

    public List<Expression> parse() {
        List<Expression> exprs = new ArrayList<>();

        do {
            Expression expr = parseExpression();
            if (expr != null) {
                exprs.add(expr);
            } else {
                break;
            }
        } while (matchToken(TokenType.SEMICOLON));

        return exprs;
    }

    public Expression parseExpression() {
        return parseExpression(Precedence.ANYTHING);
    }

    public Expression parseExpression(Precedence precedence) {
        Token token = consumeToken();

        if (token.getType().equals(TokenType.EOF)) {
            return null;
        }

        PrefixParselet parselet = prefixParselets.get(token.getType().symbol);

        if (parselet == null) {
            throw new RuntimeException("Cannot parse " + token.getType().name() + " expression");
        }

        Expression expr = parselet.parse(this, token);
        initExpr(expr, token);

        return parseInfixExpression(expr, precedence);
    }

    private Expression parseInfixExpression(Expression left, Precedence precedence) {
        Token token;

        while (precedence.ordinal() < getPrecedence().ordinal()) {
            token = consumeToken();
            left = infixParselets.get(token.getType().symbol).parse(this, token, left);
            initExpr(left, token);
        }

        return left;
    }

    private void initExpr(Expression expression, Token token) {
        expression.getAttributes().put("position", token.getPosition());
    }

    private Precedence getPrecedence() {
        Token token = readToken();

        if (token != null) {
            InfixParselet parselet = infixParselets.get(token.getType().symbol);

            if (parselet != null) {
                return parselet.getPrecedence();
            }
        }

        return Precedence.ANYTHING;
    }

    public List<Expression> parseArgs() {
        List<Expression> args = new ArrayList<>();

        if (matchToken(TokenType.BRACKET_LEFT)) {
            if (!matchToken(TokenType.BRACKET_RIGHT)) { // check for empty groups
                do {
                    args.add(parseExpression());
                } while (matchToken(TokenType.COMMA));

                consumeToken(TokenType.BRACKET_RIGHT);
            }
        }

        return args;
    }

    public String fixNameShortcut(String name) {
        String[] splits = name.split("\\.");

        switch (splits[0]) {
            case "q" -> splits[0] = "query";
            case "v" -> splits[0] = "variable";
            case "t" -> splits[0] = "temp";
            case "c" -> splits[0] = "context";
        }

        return String.join(".", splits);
    }

    public String getNameHead(String name) {
        return name.split("\\.")[0];
    }

    public Token consumeToken() {
        return consumeToken(null);
    }

    public Token consumeToken(TokenType expectedType) {
        tokenIterator.step();
        Token token = readToken();

        if (expectedType != null) {
            if (!token.getType().equals(expectedType)) {
                throw new RuntimeException("Expected token " + expectedType.name() + " and " + token.getType().name() + " given");
            }
        }

        return readTokens.remove(0);
    }

    public boolean matchToken(TokenType expectedType) {
        return matchToken(expectedType, true);
    }

    public boolean matchToken(TokenType expectedType, boolean consume) {
        Token token = readToken();

        if (token == null || !token.getType().equals(expectedType)) {
            return false;
        } else {
            if (consume) {
                consumeToken();
            }

            return true;
        }
    }

    private Token readToken() {
        return readToken(0);
    }

    private Token readToken(int distance) {
        while (distance >= readTokens.size()) {
            readTokens.add(tokenIterator.next());
        }

        return readTokens.get(distance);
    }
}

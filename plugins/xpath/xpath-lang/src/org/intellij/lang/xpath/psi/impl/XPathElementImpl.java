/*
 * Copyright 2005 Sascha Weinreuter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.intellij.lang.xpath.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import org.intellij.lang.xpath.XPathElementTypes;
import org.intellij.lang.xpath.XPathFileType;
import org.intellij.lang.xpath.XPathTokenTypes;
import org.intellij.lang.xpath.psi.XPathElement;

public class XPathElementImpl extends ASTWrapperPsiElement implements XPathElement {

    public XPathElementImpl(ASTNode node) {
        super(node);
    }

    @NotNull
    public Language getLanguage() {
        return XPathFileType.XPATH.getLanguage();
    }

    public String toString() {
        final String name = getClass().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ": " + getText();
    }

    public PsiElement addBefore(@NotNull PsiElement psiElement, final PsiElement anchor) throws IncorrectOperationException {
        final ASTNode node = getNode();
        final ASTNode child = psiElement.getNode();
        assert child != null;
        node.addChild(child, anchor.getNode());
        return node.getPsi();
    }

    public PsiElement addAfter(@NotNull PsiElement psiElement, final PsiElement anchor) throws IncorrectOperationException {
        final ASTNode astNode = anchor.getNode();
        assert astNode != null;
        final ASTNode next = astNode.getTreeNext();

        final ASTNode node = getNode();
        final ASTNode newNode = psiElement.getNode();
        assert newNode != null;
        if (next != null) {
            node.addChild(newNode, next);
        } else {
            node.addChild(newNode);
        }
        return node.getPsi();
    }

    public PsiElement add(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        final ASTNode child = psiElement.getNode();
        assert child != null;
        getNode().addChild(child);
        return getNode().getPsi();
    }

    public void delete() throws IncorrectOperationException {
        final ASTNode node = getNode();

        final ASTNode parent = node.getTreeParent();
        final ASTNode next = node.getTreeNext();
        parent.removeChild(node);

        if (XPathElementTypes.EXPRESSIONS.contains(node.getElementType())) {
            if (parent.getElementType() == XPathElementTypes.FUNCTION_CALL) {
                if (next != null && next.getElementType() == XPathTokenTypes.COMMA) {
                    parent.removeChild(next);
                }
            }
        }
    }

    public PsiElement replace(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        final ASTNode newNode = psiElement.getNode();
        final ASTNode myNode = getNode();

        assert newNode != null;
        myNode.getTreeParent().replaceChild(myNode, newNode);

        return newNode.getPsi();
    }

    @NotNull
    @SuppressWarnings({ "ConstantConditions", "EmptyMethod" })
    public final ASTNode getNode() {
        return super.getNode();
    }
}

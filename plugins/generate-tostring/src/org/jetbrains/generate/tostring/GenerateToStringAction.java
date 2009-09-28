/*
 * Copyright 2001-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.generate.tostring;

import com.intellij.openapi.editor.actionSystem.EditorAction;

/**
 * The IDEA action for this plugin.
 * <p/>
 * This action handles the generation of a <code>toString()</code> method that dumps the fields
 * of the class.
 */
public class GenerateToStringAction extends EditorAction {

    /**
     * Constructor.
     */
    public GenerateToStringAction() {
        super(new GenerateToStringActionHandlerImpl()); // register our action handler
    }
}
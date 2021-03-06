/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.java.jdt.internal.compiler.lookup;

import com.codenvy.ide.ext.java.jdt.core.compiler.CharOperation;
import com.codenvy.ide.ext.java.jdt.internal.compiler.ClassFileConstants;

import java.util.ArrayList;
import java.util.List;

public class MissingTypeBinding extends BinaryTypeBinding {

    /**
     * Special constructor for constructing proxies of missing types (114349)
     *
     * @param packageBinding
     * @param compoundName
     * @param environment
     */
    public MissingTypeBinding(PackageBinding packageBinding, char[][] compoundName, LookupEnvironment environment) {
        this.compoundName = compoundName;
        computeId();
        this.tagBits |= TagBits.IsBinaryBinding | TagBits.HierarchyHasProblems | TagBits.HasMissingType;
        this.environment = environment;
        this.fPackage = packageBinding;
        this.fileName = CharOperation.concatWith(compoundName, '/');
        this.sourceName = compoundName[compoundName.length - 1]; // [java][util][Map$Entry]
        this.modifiers = ClassFileConstants.AccPublic;
        this.superclass = null; // will be fixed up using #setMissingSuperclass(...)
        this.superInterfaces = Binding.NO_SUPERINTERFACES;
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.memberTypes = Binding.NO_MEMBER_TYPES;
        this.fields = Binding.NO_FIELDS;
        this.methods = Binding.NO_METHODS;
    }

    /** @see com.codenvy.ide.ext.java.jdt.internal.compiler.lookup.TypeBinding#collectMissingTypes(java.util.List) */
    public List collectMissingTypes(List missingTypes) {
        if (missingTypes == null) {
            missingTypes = new ArrayList(5);
        } else if (missingTypes.contains(this)) {
            return missingTypes;
        }
        missingTypes.add(this);
        return missingTypes;
    }

    /**
     * Missing binary type will answer <code>false</code> to #isValidBinding()
     *
     * @see com.codenvy.ide.ext.java.jdt.internal.compiler.lookup.Binding#problemId()
     */
    public int problemId() {
        return ProblemReasons.NotFound;
    }

    /**
     * Only used to fixup the superclass hierarchy of proxy binary types
     *
     * @param missingSuperclass
     * @see LookupEnvironment#createMissingType(PackageBinding, char[][])
     */
    void setMissingSuperclass(ReferenceBinding missingSuperclass) {
        this.superclass = missingSuperclass;
    }

    public String toString() {
        return "[MISSING:" + new String(CharOperation.concatWith(this.compoundName, '.')) + "]"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}

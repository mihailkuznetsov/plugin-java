/*******************************************************************************
 * Copyright (c) 2012-2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.extension.maven.client.projecttree;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.api.project.shared.dto.ItemReference;
import com.codenvy.api.project.shared.dto.ProjectDescriptor;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.icon.IconRegistry;
import com.codenvy.ide.api.projecttree.AbstractTreeNode;
import com.codenvy.ide.api.projecttree.TreeNode;
import com.codenvy.ide.api.projecttree.TreeSettings;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.ext.java.client.projecttree.JavaTreeStructure;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Tree structure for Maven project. It also respects multi-module projects.
 *
 * @author Artem Zatsarynnyy
 */
public class MavenProjectTreeStructure extends JavaTreeStructure {

    private IconRegistry iconRegistry;

    protected MavenProjectTreeStructure(TreeSettings settings, ProjectDescriptor project, EventBus eventBus, EditorAgent editorAgent,
                                        AppContext appContext, ProjectServiceClient projectServiceClient,
                                        DtoUnmarshallerFactory dtoUnmarshallerFactory, IconRegistry iconRegistry) {
        super(settings, project, eventBus, editorAgent, appContext, projectServiceClient, iconRegistry, dtoUnmarshallerFactory);
        this.iconRegistry = iconRegistry;
    }

    /** {@inheritDoc} */
    @Override
    public void getRoots(AsyncCallback<Array<TreeNode<?>>> callback) {
        AbstractTreeNode projectRoot =
                new MavenProjectNode(null, project, this, settings, eventBus, projectServiceClient, dtoUnmarshallerFactory);
        callback.onSuccess(Collections.<TreeNode<?>>createArray(projectRoot));
    }

    @Override
    public MavenFolderNode newJavaFolderNode(AbstractTreeNode parent, ItemReference data) {
        return new MavenFolderNode(parent, data, this, settings, eventBus, editorAgent, projectServiceClient, dtoUnmarshallerFactory);
    }

    public ModuleNode newModuleNode(AbstractTreeNode parent, ProjectDescriptor data) {
        return new ModuleNode(parent, data, this, settings, eventBus, projectServiceClient, dtoUnmarshallerFactory, iconRegistry);
    }
}

package com.castle.repo.mongo;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class HierarchicalBeforeConvertListener extends AbstractMongoEventListener<HierarchicalDoc<?, HierarchicalDoc<?, ?>>> {

	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";

	@Override
	public void onBeforeConvert(BeforeConvertEvent<HierarchicalDoc<?, HierarchicalDoc<?, ?>>> event) {
		HierarchicalDoc<?, HierarchicalDoc<?, ?>> source = event.getSource();
		HierarchicalDoc<?, ?> parent = source.getParent();
		if (parent == null) {
			source.setTreePath(TREE_PATH_SEPARATOR);
		} else {
			source.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		}
	}

}

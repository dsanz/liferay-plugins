/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.socialcoding.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

/**
 * The base model interface for the JIRAChangeItem service. Represents a row in the &quot;changeitem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.socialcoding.model.impl.JIRAChangeItemModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.socialcoding.model.impl.JIRAChangeItemImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JIRAChangeItem
 * @see com.liferay.socialcoding.model.impl.JIRAChangeItemImpl
 * @see com.liferay.socialcoding.model.impl.JIRAChangeItemModelImpl
 * @generated
 */
@ProviderType
public interface JIRAChangeItemModel extends BaseModel<JIRAChangeItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a j i r a change item model instance should use the {@link JIRAChangeItem} interface instead.
	 */

	/**
	 * Returns the primary key of this j i r a change item.
	 *
	 * @return the primary key of this j i r a change item
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this j i r a change item.
	 *
	 * @param primaryKey the primary key of this j i r a change item
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the jira change item ID of this j i r a change item.
	 *
	 * @return the jira change item ID of this j i r a change item
	 */
	public long getJiraChangeItemId();

	/**
	 * Sets the jira change item ID of this j i r a change item.
	 *
	 * @param jiraChangeItemId the jira change item ID of this j i r a change item
	 */
	public void setJiraChangeItemId(long jiraChangeItemId);

	/**
	 * Returns the jira change group ID of this j i r a change item.
	 *
	 * @return the jira change group ID of this j i r a change item
	 */
	public long getJiraChangeGroupId();

	/**
	 * Sets the jira change group ID of this j i r a change item.
	 *
	 * @param jiraChangeGroupId the jira change group ID of this j i r a change item
	 */
	public void setJiraChangeGroupId(long jiraChangeGroupId);

	/**
	 * Returns the field of this j i r a change item.
	 *
	 * @return the field of this j i r a change item
	 */
	@AutoEscape
	public String getField();

	/**
	 * Sets the field of this j i r a change item.
	 *
	 * @param field the field of this j i r a change item
	 */
	public void setField(String field);

	/**
	 * Returns the old value of this j i r a change item.
	 *
	 * @return the old value of this j i r a change item
	 */
	@AutoEscape
	public String getOldValue();

	/**
	 * Sets the old value of this j i r a change item.
	 *
	 * @param oldValue the old value of this j i r a change item
	 */
	public void setOldValue(String oldValue);

	/**
	 * Returns the old string of this j i r a change item.
	 *
	 * @return the old string of this j i r a change item
	 */
	@AutoEscape
	public String getOldString();

	/**
	 * Sets the old string of this j i r a change item.
	 *
	 * @param oldString the old string of this j i r a change item
	 */
	public void setOldString(String oldString);

	/**
	 * Returns the new value of this j i r a change item.
	 *
	 * @return the new value of this j i r a change item
	 */
	@AutoEscape
	public String getNewValue();

	/**
	 * Sets the new value of this j i r a change item.
	 *
	 * @param newValue the new value of this j i r a change item
	 */
	public void setNewValue(String newValue);

	/**
	 * Returns the new string of this j i r a change item.
	 *
	 * @return the new string of this j i r a change item
	 */
	@AutoEscape
	public String getNewString();

	/**
	 * Sets the new string of this j i r a change item.
	 *
	 * @param newString the new string of this j i r a change item
	 */
	public void setNewString(String newString);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(
		com.liferay.socialcoding.model.JIRAChangeItem jiraChangeItem);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.liferay.socialcoding.model.JIRAChangeItem> toCacheModel();

	@Override
	public com.liferay.socialcoding.model.JIRAChangeItem toEscapedModel();

	@Override
	public com.liferay.socialcoding.model.JIRAChangeItem toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}
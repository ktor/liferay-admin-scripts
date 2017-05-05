/**
 * Copyright (c) 2013 S�bastien Le Marchand, All rights reserved.
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

//
// refactor_structure_ids.groovy
//
// Refactor some structures IDs
//

// Variables to update at your convenience

groupId = 10190L // The site from getting structures

includeGlobalStructures = true

map = [
        '10904': 'HEADER-STR',
        '11501': 'PIECE-OF-NEWS-STR',
        '11803': 'FOOTER-STR'
]

// Implementation
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil
import com.liferay.portlet.journal.model.JournalArticle
import com.liferay.portlet.journal.model.JournalTemplate
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil

try {

    map.each { oldId, newId ->
        s = JournalStructureLocalServiceUtil.getStructure(groupId, oldId, includeGlobalStructures)
        s.setStructureId(newId)
        JournalStructureLocalServiceUtil.updateJournalStructure(s)

        q = DynamicQueryFactoryUtil.forClass(JournalTemplate.class)
        q.add(PropertyFactoryUtil.forName('structureId').eq(oldId))
        templates = JournalTemplateLocalServiceUtil.dynamicQuery(q)
        templates.each { t ->
            t.setStructureId(newId)
            JournalTemplateLocalServiceUtil.updateJournalTemplate(t)
        }

        q = DynamicQueryFactoryUtil.forClass(JournalArticle.class)
        q.add(PropertyFactoryUtil.forName('structureId').eq(oldId))
        articles = JournalArticleLocalServiceUtil.dynamicQuery(q)
        articles.each { a ->
            a.setStructureId(newId)
            JournalArticleLocalServiceUtil.updateJournalArticle(a)
        }
    }

} catch (e) {
    e.printStackTrace(out)
}

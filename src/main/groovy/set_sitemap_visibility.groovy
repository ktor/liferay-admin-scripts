import com.liferay.portal.model.Layout
import com.liferay.portal.service.LayoutLocalServiceUtil
import com.liferay.portal.kernel.util.UnicodeProperties

try {
    sitemapSettings = [
            '/some_friendly_url', true,
            '/another_friendly_url', false
    ]

    layouts = new ArrayList<Layout>(2000)
    groupId = 12345l
    layouts.addAll(LayoutLocalServiceUtil.getLayouts(groupId, false))
    i = 1
    layouts.each { l ->
        show = sitemapSettings[l.getFriendlyURL()]
        sitemapSettings.remove(l.getFriendlyURL())
        if (show != null) {
            if (show) {
                updateSitemapableTypeSettingProperty(l, show)
                out.println(i++ + ' ' + l.getFriendlyURL() + ": " + show)
            } else {
                updateSitemapableTypeSettingProperty(l, show)
                out.println(i++ + ' ' + l.getFriendlyURL() + ": " + show)
            }
        }
    }

    out.println("Not found:")
    sitemapSettings.each { key, value ->
        out.println(key)
    }
} catch (e) {
    e.printStackTrace(out)
}

private void updateSitemapableTypeSettingProperty(Layout l, show) {
    UnicodeProperties typeSettingsProperties = new UnicodeProperties()
    typeSettingsProperties.fastLoad(l.getTypeSettings())
    typeSettingsProperties.setProperty("sitemap-include", show ? "1" : "0")
    l.setTypeSettings(typeSettingsProperties.toString())
    LayoutLocalServiceUtil.updateLayout(l)
}

String key = "campaign.count.url";
String value = "/delegate/cmm/campaignCountMSISDN";

import com.liferay.portal.kernel.util.PrefsPropsUtil
import com.liferay.portal.security.auth.CompanyThreadLocal

import javax.portlet.PortletPreferences

try {
    PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(CompanyThreadLocal.getCompanyId(), false);
    out.println(PrefsPropsUtil.getString(CompanyThreadLocal.getCompanyId(), key));
    portletPreferences.setValues(key, value)
    portletPreferences.store();
    out.println(PrefsPropsUtil.getString(CompanyThreadLocal.getCompanyId(), key));
} catch (Exception e) {
    e.printStackTrace(out)
}

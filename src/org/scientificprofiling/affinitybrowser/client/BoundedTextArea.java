package org.scientificprofiling.affinitybrowser.client;

import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;

/**
 * Class works like TextField class but Max Length specifies the maximum
 * amount user can type, instead of being used as limit for validation.
 *
 * @author Scott Selikoff
 */
public class BoundedTextArea extends TextArea {
	@Override
	public void setMaxLength(int m) {
		super.setMaxLength(m);
		if (rendered) {
			getInputEl().setElementAttribute("maxLength", m);
		}
	}

	@Override
	protected void onRender(Element target, int index) {
		super.onRender(target, index);
		getInputEl().setElementAttribute("maxLength", getMaxLength());
	}
}

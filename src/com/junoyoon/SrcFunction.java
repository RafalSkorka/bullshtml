/**
 * Copyright (C) 2009 JunHo Yoon
 * Copyright (C) 2015 Rafal Skorka
 *
 * bullshtml is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * bullshtml is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 */

package com.junoyoon;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.uwyn.jhighlight.tools.StringUtils;

public class SrcFunction {
	public SrcFunction() {
	}

	public SrcFunction init(Element element) {
		String name = element.getAttributeValue("name");
		this.name = name.contains(")") ? name : name + "()";
		this.branchCount = Integer.parseInt(element.getAttributeValue("cd_total"));
		this.coveredBranchCount = Integer.parseInt(element.getAttributeValue("cd_cov"));
		this.covered = "1".equals(element.getAttributeValue("fn_cov"));
		boolean isFirst = true;
		for (Object elementObject : element.getChildren()) {
			Element eachProbe = (Element) elementObject;
			if (!eachProbe.getName().equals("probe")) {
				continue;
			}
			if (isFirst) {
				isFirst = false;
				this.line = Integer.parseInt(eachProbe.getAttributeValue("line"));
				continue;
			}
			SrcDecisionPoint decisionPoint = SrcDecisionPoint.createDecisionPoint(eachProbe);
			if (decisionPoint != null) {
				for (SrcDecisionPoint look : this.decisionPoints)
				{
					if (look.line == decisionPoint.line)
					{
						decisionPoint.sequence = true;
						look.sequence = true;
						break;
					}
				}
				
				this.decisionPoints.add(decisionPoint);
			}
		}
		return this;
	}

	public String name;
	public boolean covered;
	public int branchCount;
	public int coveredBranchCount;
	public int line;
	public static String format = new String("%.1f");
	public List<SrcDecisionPoint> decisionPoints = new ArrayList<SrcDecisionPoint>();

	public int getCoveredCount() {
		return this.covered ? 1 : 0;
	}

	public String getXmlEncodedName() {
		return StringUtils.encodeHtml(this.name);
	}

	public int getComplexity() {
		int count = 1;
		for (SrcDecisionPoint decisionPoint : this.decisionPoints) {
			count += decisionPoint.decisionType.complexity;
		}
		return count;
	}

	public String getBranchCoverage() {
		if (this.branchCount == 0) {
			return "N/A";
		}
		return String.format(SrcFunction.format, ((float) this.coveredBranchCount / this.branchCount * 100));
	}

	public String getBranchCoverageStyle() {
		String bc = getBranchCoverage();
		return bc.equals("N/A") ? "class='na' style='width:100px'" : "class='greenbar' style='width:" + bc + "px'";
	}

}

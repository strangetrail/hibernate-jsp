package generate.table;

public class TableGenerator {
	public static final Integer numberOfRows = 5;
	public static final Integer minimumLeftRightItems = 3;
	public static final Integer maximumLeadingTrailingItems = 3;

	private String listItem(Integer ind) {
		StringBuilder sbItem = new StringBuilder();
		sbItem.append("<li class=\"inliner\">");
		sbItem.append("<input type=\"button\" class=\"scrollbutton\" onclick=\"showpage('" + ind + "')\" value=\"" + ind + "\"/>");
		sbItem.append("</li>");
		return sbItem.toString();
	}
	
	public String generateGeneral(int startRowNumber, int totalRows, int totalPages, int currentIndex) {
		int i,j, leftSide, rightSide;
		StringBuilder sbContent = new StringBuilder();
		sbContent.append("<table class=\"gentable\">");
		for (i = startRowNumber; i<totalRows+startRowNumber; i++) {
			sbContent.append("<tr>");
			for (j = 0; j<5; j++) {
				sbContent.append("<td class=\"gentable\">");
				sbContent.append("" + i + "." + j);
				sbContent.append("</td>");
			}
			sbContent.append("</tr>");
		}
		sbContent.append("</table>");
		sbContent.append("<ul class=\"inlinelist\">");
		if (currentIndex > minimumLeftRightItems+maximumLeadingTrailingItems) {
			leftSide = currentIndex - maximumLeadingTrailingItems;
			for (i=0; i<minimumLeftRightItems; i++) {
				sbContent.append(listItem(i));	
			}
			sbContent.append("<li class=\"inliner\">...</li>");
		}
		else
			leftSide = 0;
		for (i=leftSide; i<currentIndex; i++) {
			sbContent.append(listItem(i));
		}
		sbContent.append(listItem(currentIndex));
		if (totalPages - currentIndex > minimumLeftRightItems+maximumLeadingTrailingItems+1) {
			rightSide = currentIndex+maximumLeadingTrailingItems+1;
		}
		else {
			rightSide = totalPages;
		}
		for (i=currentIndex+1;i<rightSide; i++) {
			sbContent.append(listItem(i));
		}
		if (rightSide < totalPages) {
			sbContent.append("<li class=\"inliner\">...</li>");
			for (i=totalPages-minimumLeftRightItems; i<totalPages; i++)
				sbContent.append(listItem(i));
		}
		sbContent.append("</ul>");
		return sbContent.toString();
	}
}

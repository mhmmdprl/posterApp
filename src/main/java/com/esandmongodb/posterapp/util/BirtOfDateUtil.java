package com.esandmongodb.posterapp.util;

import java.time.LocalDate;

public class BirtOfDateUtil {

	public static boolean checkBirtOfDate(LocalDate birtOfDate) {

		LocalDate localDate = LocalDate.now();
		if (localDate != null) {
			if (localDate.getMonthValue() == birtOfDate.getMonthValue()
					&& localDate.getDayOfMonth() == birtOfDate.getDayOfMonth()) {
				return true;

			}
		}

		return false;
	}

}

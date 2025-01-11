import {isNil} from "lodash";
import moment, {Moment} from "moment";
import {PatentUsageDescriptions} from "app/shared/types/patent.types";


const defaultValue = '-';
const defaultSeparator = '-';

export const getDetail = (detail: any): string => {
	if (isNil(detail) || String(detail).trim() === '') {
		return defaultValue;
	}
	return String(detail);
}

export const isPatentDetailEmpty = (detail: any): boolean => {
	return getDetail(detail) === defaultValue;
}

export const getDateDetail = (detail: any, expectedDateFormat: string): string => {
	if (isPatentDetailEmpty(detail)) {
		return defaultValue;
	}
	const date = moment(detail, expectedDateFormat);
	if (!date.isValid()) {
		return parseDateWithInvalidFormat(detail, expectedDateFormat);
	}
	return parseToDefault(date);
}

export const isUsageDescEmpty = (descriptions: PatentUsageDescriptions): boolean => {
	return isNil(descriptions) || Object.keys(descriptions).length === 0;
}

const parseDateWithInvalidFormat = (dateDetail: any, format: string): string => {
	const separator = getDateSeparator(format);
	let dateString = String(dateDetail);
	let offset = 0;
	for (let i = 0; i < format.length; i++) {
		if (format.charAt(i) === separator) {
			dateString = `${dateString.slice(0, i + offset)}${separator}${dateString.slice(i + offset)}`;
			offset += 1;
		}
	}
	const date = moment(dateString, format);
	return parseToDefault(date);
}

const getDateSeparator = (format: string): string => {
	const match = format.match(/[^A-Za-z0-9]/);
	return match ? match[0] : defaultSeparator;
}

const parseToDefault = (date: Moment): string => {
	return date.format(['DD', 'MM', 'yyyy'].join(defaultSeparator));
}

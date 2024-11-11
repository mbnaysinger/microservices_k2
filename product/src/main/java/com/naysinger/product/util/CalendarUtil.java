package com.naysinger.product.util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalendarUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;	
    private static final String MASCARA_DATA = "dd/MM/yyyy";	
    
	public static final String GMT;	
	public static final String TIMEZONE = "America/Sao_Paulo";
	
	static {
		GMT = getGMTAtual();
	}

	private static final Logger LOGGER = Logger.getLogger(CalendarUtil.class.getName());

	protected static final String PATTERN = "dd-MM-yyyy HH:mm:ss";

	public Calendar getDataAtual() {
		return Calendar.getInstance();
	}
	
    public static Date hoje() {
        return Calendar.getInstance().getTime();
    }
    
    //Feito desta forma pois o TimeZone.getTimeZone("GMT") nao estava funcionando...
    public static Date hojeUTC() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -3);
        return cal.getTime();
    }    
    
    public static String formataData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat(MASCARA_DATA);
        if (data == null) {
            return "";
        }

        return sdf.format(data);
    }
    
    public static String formataDataCompleta(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        if (data == null) {
            return "";
        }

        return sdf.format(data);
    }
    
    public static Calendar formataData(String data) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(MASCARA_DATA);
        Date dt = null;
        try {
            dt = sdf.parse(data);
        } catch (ParseException e) {
        	
        }
        cal.setTime(dt);
        return cal;
    }
    
    public static Calendar formataDataCompleta(String data) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        Date dt = null;
        try {
            dt = sdf.parse(data);
        } catch (ParseException e) {
        	
        }
        cal.setTime(dt);
        return cal;
    }
	
	public static Date getDataMenosUmMes() {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -30);
	    return cal.getTime();
	}
	
	public static Date getDataAtualSemHora() {
	    Calendar calendario = Calendar.getInstance();
	    calendario.set(Calendar.HOUR_OF_DAY, 0);
	    calendario.set(Calendar.MINUTE, 0);
	    calendario.set(Calendar.SECOND, 0);
        calendario.set(Calendar.MILLISECOND, 0);

        return calendario.getTime();
    }

	public String calendarToString(final Calendar data) {
		return calendarToString(data, null);
	}

	public String calendarToString(final Calendar data, String mask) {
		if (data == null)
			return "";

		return getSimpleDateFormat(mask).format(data.getTime());
	}

	public Calendar stringToCalendar(final String data) {
		return stringToCalendar(data, null);
	}

	protected SimpleDateFormat getSimpleDateFormat(String mask) {
		return new SimpleDateFormat(getDatePatternString(mask));
	}

	protected String getDatePatternString(String mask) {
		return StringUtils.isBlank(mask) ? PATTERN : mask;
	}

	public Calendar stringToCalendar(final String data, String mask) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(getSimpleDateFormat(mask).parse(data));
		} catch (ParseException | IllegalArgumentException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
		
		return cal;
	}
	
    public static Calendar formataData(String data, String mask) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        Date dt = null;
        try {
            dt = sdf.parse(data);
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
        cal.setTime(dt);
        return cal;
    }

	public boolean isDataDentroDoPeriodo(Calendar data, Calendar dataInicialPeriodo, Calendar dataFinalPeriodo) {
		return data.after(dataInicialPeriodo) && data.before(dataFinalPeriodo);
	}

	public List<String> buildYearStringList(Integer year, Integer yearPast) {
		List<String> result = new ArrayList<>();
		Integer maxYear = year;
		while(maxYear >= yearPast){
			result.add(maxYear.toString());
			maxYear--;
		}
		
		return result;
	}
	
	public static String getGMTAtual() {
		//GMT-3:00
		SimpleDateFormat sdf = new SimpleDateFormat("XXX");
		return "GMT"+sdf.format(new Date());
	}

}
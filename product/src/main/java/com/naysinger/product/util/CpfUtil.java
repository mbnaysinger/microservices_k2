package com.naysinger.product.util;

import org.apache.commons.lang3.StringUtils;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import br.org.fiergs.prdws.entity.pk.PessoasVwPk;

public class CpfUtil {
	
	private static final Logger LOGGER = Logger.getLogger(CpfUtil.class.getName());

	private static final String REGEX_CLEAN_CPF = "\\.|\\-|\\/";
	private static final char ZERO = '0';

	private static final int TAMANHO_CPF = 11;
	
	private static final String CPF_0 = "00000000000";
	private static final String CPF_1 = "11111111111";
	private static final String CPF_2 = "22222222222";
	private static final String CPF_3 = "33333333333";
	private static final String CPF_4 = "44444444444";
	private static final String CPF_5 = "55555555555";
	private static final String CPF_6 = "66666666666";
	private static final String CPF_7 = "77777777777";
	private static final String CPF_8 = "88888888888";
	private static final String CPF_9 = "99999999999";

	private static final String[] SEQUENCIAS_REPETIDAS = { CPF_0, CPF_1, CPF_2, CPF_3, CPF_4, CPF_5, CPF_6, CPF_7,
			CPF_8, CPF_9 };

	private CpfUtil() {
		super();
	}

	public static String clean(String cpf) {
		if (StringUtils.isEmpty(cpf))
			return null;

		return StringUtils.leftPad(cpf.replaceAll(REGEX_CLEAN_CPF, ""), TAMANHO_CPF, ZERO);
	}

	public static String format(Long id1, Long id3) {
		return format(StringUtils.leftPad(id1.toString(), 9, ZERO) + StringUtils.leftPad(id3.toString(), 2, ZERO));
	}

	/*public static PessoasVwPk extrairPKFrom(Long cpf) {
		if (cpf == null)
			return null;

		return extrairPKFrom(cpf.toString());
	}

	public static PessoasVwPk extrairPKFrom(String cpf) {
		if (StringUtils.isEmpty(cpf))
			return null;

		String cCpf = StringUtils.leftPad(clean(cpf), TAMANHO_CPF, ZERO);

		if (cCpf.length() > TAMANHO_CPF)
			return null;

		PessoasVwPk pessoaPK = new PessoasVwPk();
		pessoaPK.setId1(Long.valueOf(cCpf.substring(0, 9)));
		pessoaPK.setId2(0L);
		pessoaPK.setId3(Long.valueOf(cCpf.substring(9, 11)));

		return pessoaPK;
	}*/

	public static String format(String cpf) {
		String scpf = clean(cpf);
		
		if(scpf == null) {
		    return cpf;
		}
		
		if (scpf.length() == 11) {
			return scpf.substring(0, 3) + "." + scpf.substring(3, 6) + "." + scpf.substring(6, 9) + "-"
					+ scpf.substring(9);
		} else {
			return scpf;
		}
	}
	
	public static boolean validaCpf(String pcpf) {
		if (pcpf == null) {
			return false;
		}

		String cpf = clean(pcpf);

		if (cpf == null || checkCpfIsSequenciaRepetida(cpf) || (cpf.length() != TAMANHO_CPF)) {
			return false;
		}

		try {
			return checkDigitosVerificadores(cpf);

		} catch (InputMismatchException erro) {
			LOGGER.log(Level.FINE, erro.getMessage(), erro);
			return false;
		}
	}
	
	protected static Boolean checkDigitosVerificadores(String cpf) {
		char dig10;
		char dig11;
		int sm;
		int i;
		int r;
		int num;
		int peso;

		// PRIMEIRO DIGITO VERIFICADOR
		sm = 0;
		peso = 10;

		for (i = 0; i < 9; i++) {
			num = cpf.charAt(i) - 48;
			sm = sm + (num * peso);
			peso = peso - 1;
		}

		r = 11 - (sm % 11);

		if ((r == 10) || (r == 11)) {
			dig10 = ZERO;
		} else {
			dig10 = (char) (r + 48);
		}

		// SEGUNDO DIGITO VERIFICADOR
		sm = 0;
		peso = 11;

		for (i = 0; i < 10; i++) {
			num = cpf.charAt(i) - 48;
			sm = sm + (num * peso);
			peso = peso - 1;
		}

		r = 11 - (sm % 11);

		if (r == 10 || r == 11) {
			dig11 = ZERO;
		} else {
			dig11 = (char) (r + 48);
		}

		// VERIFICA SE OS DIGITOS CALCULADOS CONFEREM COM OS DIGITOS
		// INFORMADOS.
		if (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10)) {
			return true;
		}
		return false;
	}
	
	protected static Boolean checkCpfIsSequenciaRepetida(String cpf) {
		for (String sequencia : SEQUENCIAS_REPETIDAS) {
			if (sequencia.equals(cpf)) {
				return true;
			}
		}
		return false;
	}
}

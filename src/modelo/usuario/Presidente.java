package modelo.usuario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import modelo.conta.ContaPoupanca;
import modelo.contas.ContaCorrente;

public class Presidente extends Funcionario {	
	
	
	public Presidente(String nome, String cpf, String senha) {
		super(nome, cpf, senha);
		this.cargo = "Presidente";
	}
	
	public String getCargo() {
		return this.cargo;
	}
	
	public void exportaDoc(ArrayList <String> relatorio) throws IOException {

		Date data = new Date(System.currentTimeMillis());		

		String local = "src/exportaDoc/relatorioPresidente" + data.getTime() + ".txt";
		File file = new File(local);
		try {
			if (file.createNewFile()) {
				System.out.println("Arquivo Criado!");
			} else {
				System.out.println("Arquivo ja existe!");
			}
		} catch (IOException e) {
			System.out.println("Erro!" + e);
		}

		FileWriter f = new FileWriter(file.getAbsoluteFile());
		for(String i : relatorio) {
			f.write(i + "\n");
		}		
		f.close();
	}

	public void chamaExportaDoc(ArrayList <String> relatorio) throws IOException {
		this.exportaDoc(relatorio);
	}

	public ArrayList<String> relatorioDiretor(Map<String, ContaCorrente> contaCorrente, Map<String, ContaPoupanca> contaPoupanca) {

		Set<String> cc = contaCorrente.keySet();
		Set<String> cp = contaPoupanca.keySet();

		ArrayList<String> info = new ArrayList<>();

		for (String c : cc) {

			String linha = ((Map<String, ContaCorrente>) contaCorrente).get(c).getTitular().getNome() + " - "
					+ ((Map<String, ContaCorrente>) contaCorrente).get(c).getTitular().formatCpf() + " - "
					+ ((Map<String, ContaCorrente>) contaCorrente).get(c).getIdAgencia().getNumeroAgencia();

			info.add(linha);

		}

		for (String c : cp) {

			String linha = ((Map<String, ContaPoupanca>) contaPoupanca).get(c).getTitular().getNome() + " - "
					+ ((Map<String, ContaPoupanca>) contaPoupanca).get(c).getTitular().formatCpf() + " - "
					+ ((Map<String, ContaPoupanca>) contaPoupanca).get(c).getIdAgencia().getNumeroAgencia();

			info.add(linha);
		}
		
		Collections.sort(info);
		
		return info;		
	}

	public ArrayList <String> relatorioPresidente(Map<String, ContaCorrente> contaCorrente, Map<String, ContaPoupanca> contaPoupanca) {
		
		Set<String> cc = contaCorrente.keySet();
		Set<String> cp = contaPoupanca.keySet();
		ArrayList<String> list = new ArrayList<>();
		
		Double capitalTotal = 0.0;
		
		for(String c : cc) {
			 capitalTotal += ((Map<String, ContaCorrente>) contaCorrente).get(c).getSaldo();
		}
		
		for(String c : cp) {
			 capitalTotal += ((Map<String, ContaPoupanca>) contaPoupanca).get(c).getSaldo();
		}
		
		String string =  "Capital total: R$ " + String.format("%.2f", capitalTotal);
		list.add(string);
		return list;
	}
}


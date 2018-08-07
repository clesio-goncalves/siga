package siga.capau.controller;

import java.util.ArrayList;
import java.util.List;

public class Teste {
	
	public static void main(String[] args) {
		List<String> nomes = new ArrayList<>();
		nomes.add("Clesio");
		nomes.add("Maria");
		nomes.add("Joao");
		nomes.add("Jose");
		
		if (nomes.contains("Clesio")) {
			System.out.println("Contem");
		}
		
	}

}

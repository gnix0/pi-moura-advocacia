export type Categoria =
	| "Cível"
	| "Penal"
	| "Trabalhista"
	| "Tributário"
	| "Família";
export type Prioridade = "Alta" | "Média" | "Prazo Hoje" | "Liminar";
export type Instancia = "1º Grau" | "2º Grau" | "STJ/STF";

export interface ProcessCard {
	id: string;
	titulo: string;
	numeroProcesso: string;
	cliente: string;
	prazo: string;
	comarca: string;
	categoria: Categoria;
	prioridade: Prioridade;
	instancia: Instancia;
	status: string;
}

export interface Column {
	id: string;
	title: string;
}

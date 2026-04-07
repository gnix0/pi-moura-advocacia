export type AreaDireito = 'Cível' | 'Penal' | 'Trabalhista' | 'Tributário' | 'Família';
export type Urgencia = 'Alta' | 'Média' | 'Prazo Hoje' | 'Liminar';
export type Instancia = '1º Grau' | '2º Grau' | 'STJ/STF';

export interface ProcessCard {
  id: string;
  titulo: string;
  numeroProcesso: string;
  cliente: string;
  prazo: string;
  varaComarca: string;
  areaDireito: AreaDireito;
  urgencia: Urgencia;
  instancia: Instancia;
  columnId: string;
}

export interface Column {
  id: string;
  title: string;
  cards: ProcessCard[];
}

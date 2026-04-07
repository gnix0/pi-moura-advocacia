import { ProcessCard, Column } from '../types/kanban';

export const initialCards: ProcessCard[] = [
  {
    id: '1',
    titulo: 'Ação de Indenização',
    numeroProcesso: '0001234-56.2026.8.09.0001',
    cliente: 'João Silva',
    prazo: '15/04/2026',
    varaComarca: '3ª Vara Cível',
    areaDireito: 'Cível',
    urgencia: 'Alta',
    instancia: '1º Grau',
    columnId: 'novos-casos'
  },
  {
    id: '2',
    titulo: 'Divórcio Consensual',
    numeroProcesso: '0002345-67.2026.8.09.0002',
    cliente: 'Maria Oliveira',
    prazo: '10/04/2026',
    varaComarca: '1ª Vara de Família',
    areaDireito: 'Família',
    urgencia: 'Média',
    instancia: '1º Grau',
    columnId: 'novos-casos'
  },
  {
    id: '3',
    titulo: 'Recurso Trabalhista',
    numeroProcesso: '0003456-78.2025.5.09.0003',
    cliente: 'Pedro Santos',
    prazo: '08/04/2026',
    varaComarca: '2ª Vara do Trabalho',
    areaDireito: 'Trabalhista',
    urgencia: 'Prazo Hoje',
    instancia: '2º Grau',
    columnId: 'em-andamento'
  },
  {
    id: '4',
    titulo: 'Habeas Corpus',
    numeroProcesso: '0004567-89.2026.8.09.0004',
    cliente: 'Carlos Mendes',
    prazo: '07/04/2026',
    varaComarca: '1ª Vara Criminal',
    areaDireito: 'Penal',
    urgencia: 'Liminar',
    instancia: '1º Grau',
    columnId: 'em-andamento'
  },
  {
    id: '5',
    titulo: 'Mandado de Segurança',
    numeroProcesso: '0005678-90.2026.4.01.0005',
    cliente: 'Ana Costa',
    prazo: '20/04/2026',
    varaComarca: 'Vara Federal',
    areaDireito: 'Tributário',
    urgencia: 'Alta',
    instancia: 'STJ/STF',
    columnId: 'aguardando'
  },
  {
    id: '6',
    titulo: 'Ação Revisional',
    numeroProcesso: '0006789-01.2025.8.09.0006',
    cliente: 'Roberto Lima',
    prazo: '25/04/2026',
    varaComarca: '2ª Vara Cível',
    areaDireito: 'Cível',
    urgencia: 'Média',
    instancia: '1º Grau',
    columnId: 'aguardando'
  },
  {
    id: '7',
    titulo: 'Acordo Homologado',
    numeroProcesso: '0007890-12.2025.5.09.0007',
    cliente: 'Fernanda Alves',
    prazo: '01/03/2026',
    varaComarca: '1ª Vara do Trabalho',
    areaDireito: 'Trabalhista',
    urgencia: 'Média',
    instancia: '1º Grau',
    columnId: 'concluidos'
  },
  {
    id: '8',
    titulo: 'Sentença Transitada',
    numeroProcesso: '0008901-23.2024.8.09.0008',
    cliente: 'Lucas Ferreira',
    prazo: '15/02/2026',
    varaComarca: '4ª Vara Cível',
    areaDireito: 'Cível',
    urgencia: 'Média',
    instancia: '2º Grau',
    columnId: 'concluidos'
  }
];

export const initialColumns: Column[] = [
  {
    id: 'novos-casos',
    title: 'Novos Casos / Análise Inicial',
    cards: initialCards.filter(card => card.columnId === 'novos-casos')
  },
  {
    id: 'em-andamento',
    title: 'Em Andamento / Prazos Abertos',
    cards: initialCards.filter(card => card.columnId === 'em-andamento')
  },
  {
    id: 'aguardando',
    title: 'Aguardando (Juízo / Audiência / Cliente)',
    cards: initialCards.filter(card => card.columnId === 'aguardando')
  },
  {
    id: 'concluidos',
    title: 'Concluídos / Arquivados',
    cards: initialCards.filter(card => card.columnId === 'concluidos')
  }
];

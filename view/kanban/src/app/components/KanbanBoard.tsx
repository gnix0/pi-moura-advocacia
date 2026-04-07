import { useState } from "react";
import { Scale, Plus, Search } from "lucide-react";
import { Column, ProcessCard } from "../types/kanban";
import { initialCards } from "../data/mockData";
import { KanbanColumn } from "./KanbanColumn";
import { ProcessModal } from "./ProcessModal";
import { DeleteConfirmDialog } from "./DeleteConfirmDialog";
import { Button } from "./ui/button";
import { Input } from "./ui/input";

const initialColumns: Column[] = [
	{
		id: "novos-casos",
		title: "Novos Casos",
		cards: initialCards.filter((card) => card.columnId === "novos-casos"),
	},
	{
		id: "em-andamento",
		title: "Em Andamento",
		cards: initialCards.filter((card) => card.columnId === "em-andamento"),
	},
	{
		id: "aguardando",
		title: "Aguardando",
		cards: initialCards.filter((card) => card.columnId === "aguardando"),
	},
	{
		id: "concluidos",
		title: "Concluídos",
		cards: initialCards.filter((card) => card.columnId === "concluidos"),
	},
];

export function KanbanBoard() {
  const [columns, setColumns] =
    useState<Column[]>(initialColumns);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingCard, setEditingCard] = useState<
    ProcessCard | undefined
  >(undefined);
  const [modalMode, setModalMode] = useState<"create" | "edit">(
    "create",
  );
  const [searchTerm, setSearchTerm] = useState("");
  const [deleteDialogOpen, setDeleteDialogOpen] =
    useState(false);
  const [cardToDelete, setCardToDelete] =
    useState<ProcessCard | null>(null);

  const moveCard = (cardId: string, targetColumnId: string) => {
    setColumns((prevColumns) => {
      const newColumns = [...prevColumns];
      let movedCard = null;

      // Remove card from source column
      newColumns.forEach((column) => {
        const cardIndex = column.cards.findIndex(
          (card) => card.id === cardId,
        );
        if (cardIndex !== -1) {
          movedCard = {
            ...column.cards[cardIndex],
            columnId: targetColumnId,
          };
          column.cards = column.cards.filter(
            (card) => card.id !== cardId,
          );
        }
      });

      // Add card to target column
      if (movedCard) {
        const targetColumn = newColumns.find(
          (col) => col.id === targetColumnId,
        );
        if (targetColumn) {
          targetColumn.cards.push(movedCard);
        }
      }

      return newColumns;
    });
  };

  const handleCreateProcess = () => {
    setModalMode("create");
    setEditingCard(undefined);
    setIsModalOpen(true);
  };

  const handleEditProcess = (card: ProcessCard) => {
    setModalMode("edit");
    setEditingCard(card);
    setIsModalOpen(true);
  };

  const handleDeleteProcess = (card: ProcessCard) => {
    setCardToDelete(card);
    setDeleteDialogOpen(true);
  };

  const confirmDelete = () => {
    if (!cardToDelete) return;

    setColumns((prevColumns) => {
      const newColumns = [...prevColumns];
      newColumns.forEach((column) => {
        column.cards = column.cards.filter(
          (card) => card.id !== cardToDelete.id,
        );
      });
      return newColumns;
    });

    setDeleteDialogOpen(false);
    setCardToDelete(null);
  };

  const handleSaveProcess = (data: Omit<ProcessCard, "id">) => {
    if (modalMode === "create") {
      // Create new process
      const newCard: ProcessCard = {
        ...data,
        id: Date.now().toString(),
      };

      setColumns((prevColumns) => {
        const newColumns = [...prevColumns];
        const targetColumn = newColumns.find(
          (col) => col.id === data.columnId,
        );
        if (targetColumn) {
          targetColumn.cards.push(newCard);
        }
        return newColumns;
      });
    } else if (modalMode === "edit" && editingCard) {
      // Edit existing process
      setColumns((prevColumns) => {
        const newColumns = [...prevColumns];

        // Remove card from old column
        newColumns.forEach((column) => {
          column.cards = column.cards.filter(
            (card) => card.id !== editingCard.id,
          );
        });

        // Add updated card to new column
        const targetColumn = newColumns.find(
          (col) => col.id === data.columnId,
        );
        if (targetColumn) {
          targetColumn.cards.push({
            ...data,
            id: editingCard.id,
          });
        }

        return newColumns;
      });
    }
  };

  // Filter cards based on search term
  const filteredColumns = columns.map((column) => ({
    ...column,
    cards: column.cards.filter((card) => {
      if (!searchTerm) return true;

      const search = searchTerm.toLowerCase();
      const matchesNumero = card.numeroProcesso
        .toLowerCase()
        .includes(search);
      const matchesCliente = card.cliente
        .toLowerCase()
        .includes(search);
      const matchesTitulo = card.titulo
        .toLowerCase()
        .includes(search);

      return matchesNumero || matchesCliente || matchesTitulo;
    }),
  }));

  // Count total filtered cards
  const totalFilteredCards = filteredColumns.reduce(
    (acc, col) => acc + col.cards.length,
    0,
  );
  const totalCards = columns.reduce(
    (acc, col) => acc + col.cards.length,
    0,
  );

  return (
    <div className="flex flex-col h-screen">
      {/* Header */}
      <header className="bg-white border-b border-gray-200 px-6 py-4">
        <div className="flex items-center justify-between gap-4">
          <div className="flex items-center gap-3">
            <div className="bg-blue-600 p-2 rounded-lg">
              <Scale className="w-6 h-6 text-white" />
            </div>
            <div>
              <h1 className="text-2xl font-bold text-gray-900">
                Kanban Jurídico
              </h1>
              <p className="text-sm text-gray-500">
                Gestão de Processos e Casos
              </p>
            </div>
          </div>

          <div className="flex items-center gap-3">
            {/* Search Input */}
            <div className="relative w-80">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
              <Input
                type="text"
                placeholder="Buscar por processo, cliente ou assunto..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10 pr-4"
              />
              {searchTerm && (
                <button
                  onClick={() => setSearchTerm("")}
                  className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
                >
                  ×
                </button>
              )}
            </div>

            <Button
              className="bg-blue-600 hover:bg-blue-700"
              onClick={handleCreateProcess}
            >
              <Plus className="w-4 h-4 mr-2" />
              Novo Processo
            </Button>
          </div>
        </div>

        {/* Search Results Info */}
        {searchTerm && (
          <div className="mt-3 text-sm text-gray-600">
            Mostrando {totalFilteredCards} de {totalCards}{" "}
            processos
          </div>
        )}
      </header>

      {/* Kanban Board */}
      <div className="flex-1 overflow-x-auto bg-gray-50">
        <div className="flex gap-4 p-6 h-full min-w-max">
          {filteredColumns.map((column) => (
            <KanbanColumn
              key={column.id}
              column={column}
              onMoveCard={moveCard}
              onEditCard={handleEditProcess}
              onDeleteCard={handleDeleteProcess}
            />
          ))}
        </div>
      </div>

      {/* Process Modal */}
      <ProcessModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveProcess}
        initialData={editingCard}
        mode={modalMode}
      />

      {/* Delete Confirmation Dialog */}
      <DeleteConfirmDialog
        isOpen={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={confirmDelete}
        processTitle={cardToDelete?.titulo || ""}
        processNumber={cardToDelete?.numeroProcesso || ""}
      />
    </div>
  );
}
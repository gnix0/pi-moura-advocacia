import { useDrop } from "react-dnd";
import { Column, ProcessCard } from "../types/kanban";
import { ProcessCardComponent } from "./ProcessCard";

interface KanbanColumnProps {
	column: Column;
	processes: ProcessCard[];
	onMoveCard: (cardId: string, targetColumnId: string) => void;
	onEditCard: (card: ProcessCard) => void;
	onDeleteCard: (id: string) => void;
}

export function KanbanColumn({
	column,
	processes,
	onMoveCard,
	onEditCard,
	onDeleteCard,
}: KanbanColumnProps) {
	

	return (
		<div
			className={`flex-shrink-0 w-80 flex flex-col bg-stone-100 shadow-[0px_0px_3px_0px_rgba(0,0,0,0.30)] rounded-lg transition-colors `}
		>
			{/* Column Header */}
			<div className="px-4 py-3 border-b  rounded-t-lg">
				<div className="flex items-center justify-between">
					<h2 className="font-semibold text-gray-900">
						{column.title}
					</h2>
					<span className="bg-gray-200 text-gray-700 text-xs font-medium px-2 py-1 rounded-full">
						{processes.length}
					</span>
				</div>
			</div>

			{/* Cards Container */}
			<div className="flex-1 overflow-y-auto p-3 space-y-3">
				{processes.map((card) => (
					<ProcessCardComponent
						key={card.id}
						process={card}
						onEdit={onEditCard}
						onDelete={onDeleteCard}
					/>
				))}
				{processes.length === 0 && (
					<div className="text-center py-8 text-gray-400 text-sm">
						Nenhum processo nesta fase
					</div>
				)}
			</div>
		</div>
	);
}

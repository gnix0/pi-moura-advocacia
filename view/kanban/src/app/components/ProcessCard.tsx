import { Calendar, User, MapPin, FileText, Edit2, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import { ProcessCard } from "../types/kanban";
import { Badge } from "./ui/badge";
import { Button } from "./ui/button";

interface ProcessCardProps {
	process: ProcessCard;
	onDragStart: (event: React.DragEvent<HTMLDivElement>, process: ProcessCard) => void;
	onDelete: (id: string) => void;
}

export function ProcessCardComponent({
	process: process,
	onDragStart,
	onDelete,
}: ProcessCardProps) {
	const getAreaColor = (area: string) => {
		const colors = {
			Cível: "bg-blue-100 text-blue-800",
			Penal: "bg-red-100 text-red-800",
			Trabalhista: "bg-green-100 text-green-800",
			Tributário: "bg-yellow-100 text-yellow-800",
			Família: "bg-purple-100 text-purple-800",
		};
		return (
			colors[area as keyof typeof colors] || "bg-gray-100 text-gray-800"
		);
	};

	const getUrgenciaColor = (urgencia: string) => {
		const colors = {
			Alta: "bg-orange-100 text-orange-800",
			Média: "bg-blue-100 text-blue-800",
			"Prazo Hoje": "bg-red-100 text-red-800",
			Liminar: "bg-red-200 text-red-900 font-semibold",
		};
		return (
			colors[urgencia as keyof typeof colors] ||
			"bg-gray-100 text-gray-800"
		);
	};

	const getInstanciaColor = (instancia: string) => {
		const colors = {
			"1º Grau": "bg-gray-100 text-gray-800",
			"2º Grau": "bg-indigo-100 text-indigo-800",
			"STJ/STF": "bg-purple-100 text-purple-800",
		};
		return (
			colors[instancia as keyof typeof colors] ||
			"bg-gray-100 text-gray-800"
		);
	};

	// Format date if it's in ISO format
	const formatDate = (date: string) => {
		if (!date) return "";
		if (date.includes("-")) {
			const [year, month, day] = date.split("-");
			return `${day}/${month}/${year}`;
		}
		return date;
	};

	return (
		<div
			draggable
			onDragStart={(e) => onDragStart(e, process)}
			className={
				"bg-white rounded-lg shadow-sm border border-gray-200 p-4 cursor-move hover:shadow-md transition-all group relative "
			
			}
		>
			{/* Action Buttons */}
			<div className="absolute top-2 right-2 flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
				<Button
					variant="ghost"
					size="sm"
					className="p-1 h-7 w-7 hover:bg-blue-50 cursor-pointer"
					asChild
				>
					<Link to={`/editar/${process.id}`}>
						<Edit2 className="w-3.5 h-3.5 text-blue-600" />
					</Link>
				</Button>
				<Button
					variant="ghost"
					size="sm"
					className="p-1 h-7 w-7 hover:bg-red-50 cursor-pointer"
					onClick={(e) => {
						e.stopPropagation();
						onDelete(process.id);
					}}
				>
					<Trash2 className="w-3.5 h-3.5 text-red-600" />
				</Button>
			</div>

			{/* Título do Processo */}
			<h3 className="font-semibold text-gray-900 mb-2 pr-16">
				{process.titulo}
			</h3>

			{/* Número do Processo */}
			<div className="flex items-start gap-2 mb-3">
				<FileText className="w-4 h-4 text-gray-400 mt-0.5 flex-shrink-0" />
				<span className="text-xs text-gray-600 font-mono break-all">
					{process.numeroProcesso}
				</span>
			</div>

			{/* Cliente */}
			<div className="flex items-center gap-2 mb-2">
				<User className="w-4 h-4 text-gray-400" />
				<span className="text-sm text-gray-700">{process.cliente}</span>
			</div>

			{/* Prazo */}
			<div className="flex items-center gap-2 mb-2">
				<Calendar className="w-4 h-4 text-gray-400" />
				<span className="text-sm text-gray-700">
					{formatDate(process.prazo)}
				</span>
			</div>

			{/* Vara/Comarca */}
			<div className="flex items-center gap-2 mb-3">
				<MapPin className="w-4 h-4 text-gray-400" />
				<span className="text-sm text-gray-700">{process.comarca}</span>
			</div>

			{/* Tags */}
			<div className="flex flex-wrap gap-1.5">
				<Badge
					variant="secondary"
					className={`text-xs ${getAreaColor(process.categoria)}`}
				>
					{process.categoria}
				</Badge>
				<Badge
					variant="secondary"
					className={`text-xs ${getUrgenciaColor(process.prioridade)}`}
				>
					{process.prioridade}
				</Badge>
				<Badge
					variant="secondary"
					className={`text-xs ${getInstanciaColor(process.instancia)}`}
				>
					{process.instancia}
				</Badge>
			</div>
		</div>
	);
}

import { useEffect, useState } from "react";
import Header from "../components/Header";
import { KanbanBoard } from "../components/KanbanBoard";
import { ProcessCard } from "../types/kanban";
import { deleteProcess, getProcesses, updateProcess } from "../../api/ProcessServiceLocalstorage";

export default function Home() {
	const [processes, setProcesses] = useState<ProcessCard[]>([]);

	async function fetchProcesses() {
		try {
			const data = await getProcesses();
			setProcesses(data);
		} catch (error) {
			console.error("Erro ao buscar processos:", error);
		}
	}

	useEffect(() => {
		fetchProcesses();
	}, []);

	const handleDelete = (id: string) => {
		const confirmDelete = window.confirm("Tem certeza que deseja excluir este processo?");
		if (!confirmDelete) return;
		deleteProcess(id).then(() => fetchProcesses());
		console.log("Excluir processo com ID:", id);
	}

	async function handleMoveProcess(processId: string, newStatus: string) {
		const process = processes.find(process => process.id === processId);
		updateProcess(processId, {...process, status: newStatus }).then(() => fetchProcesses());
		console.log(`Mover processo ${processId} para status ${newStatus}`);
	}

	return (
		<main className="min-h-screen bg-gray-50">
			<Header
				title="Kanban Jurídico"
				subtitle="Gestão de Processos e Casos"
			/>
			<KanbanBoard
				processes={processes}
				onDelete={handleDelete}
				onMoveCard={handleMoveProcess}
			/>

			
		</main>
	);
}

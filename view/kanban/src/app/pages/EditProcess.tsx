import { useParams } from "react-router-dom";
import { getProcessById, updateProcess } from "../../api/ProcessServiceLocalstorage";
import Header from "../components/Header";
import ProcessForm from "../components/ProcessForm";
import { ProcessCard } from "../types/kanban";
import { useEffect, useState } from "react";

export default function EditProcess() {
	const { id } = useParams();
	const [process, setProcess] = useState<ProcessCard | undefined>(undefined);

	useEffect(() => {
		async function loadProcess() {
			if (!id) return;
			try {
        const data = await getProcessById(id);
        console.log(data);
				setProcess(data);
			} catch (error) {
				console.error("Erro ao buscar processo:", error);
			}
		}

		loadProcess();
	}, [id]);

	const handleSave = (data: Omit<ProcessCard, "id">) => {
		if (!id) return;

		updateProcess(id, data)
			.then((response) => {
				console.log("Processo atualizado com sucesso:", response);
			})
			.catch((error) => {
				console.error("Erro ao atualizar processo:", error);
			});
	};

	return (
		<main className="min-h-screen bg-gray-50">
			<Header
				title="Editar Processo"
				subtitle="Atualize os detalhes do processo"
				showActions={false}
			/>
      {process && (
        <ProcessForm
          initialData={process}
          onSave={handleSave}
          buttonLabel="Atualizar processo"
        />
      )}
		</main>
	);
}

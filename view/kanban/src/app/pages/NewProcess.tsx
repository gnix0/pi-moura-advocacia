import { createProcess } from "../../api/ProcessServiceLocalstorage";
import Header from "../components/Header";
import ProcessForm from "../components/ProcessForm";
import { ProcessCard } from "../types/kanban";

export default function NewProcess() {

  const handleSave = (data: Omit<ProcessCard, "id">) => {
    createProcess(data).then((response) => {
      console.log("Processo criado com sucesso:", response);
    }).catch((error) => {
      console.error("Erro ao criar processo:", error);
    });
  };


  return (
    <main className="min-h-screen bg-gray-50">
      <Header title="Novo Processo" subtitle="Preencha os detalhes do processo" showActions={false} />
      <ProcessForm onSave={handleSave}/>
    </main>
  )
}
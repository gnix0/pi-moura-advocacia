import Home from './pages/Home';
import { Route, Routes } from 'react-router-dom';
import NewProcess from './pages/NewProcess';
import EditProcess from './pages/EditProcess';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/novo-processo" element={<NewProcess />} />
      <Route path="/editar/:id" element={<EditProcess />} />
    </Routes>
  );
}

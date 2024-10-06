export default function StatusMessage({loading, error}){
    if (loading) {
      return <p className="text-white text-center mt-10">Carregando...</p>
    }
  
    if (error) {
      return <p className="text-white text-center mt-10">O servidor encontrou um erro ao exibir os cursos.</p>
    }
  
    return null
  }
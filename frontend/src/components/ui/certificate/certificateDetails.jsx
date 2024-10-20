import { TableLayout } from './tableLayout'

export default function CertificateDetails({ data }) {
  return (
    <table className="w-96">
      <TableLayout head="Nome" body={data.username} />
      <TableLayout head="Curso" body={data.courseName} />
      <TableLayout head="Início" body={data.courseStartedAt} />
      <TableLayout head="Fim" body={data.courseFinishedAt} />
      <TableLayout head="Emissão" body={data.issuedAt} />
    </table>
  )
}

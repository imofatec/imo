export function TableLayout({ head, body }) {
  return (
    <tr className="even:bg-custom-blue">
      <th className="px-6 py-3 text-left uppercase border border-collapse">
        {head}
      </th>
      <td className="px-6 py-3 text-left border border-collapse">{body}</td>
    </tr>
  )
}

type SocialAccountsProps = {
  providers: React.ComponentType[]
}

export default function SocialAccounts({ providers }: SocialAccountsProps) {
  return (
    <div className="flex justify-center gap-3">
      {providers.map((Icon, index) => (
        <Icon key={index}/>
      ))}
    </div>
  )
}

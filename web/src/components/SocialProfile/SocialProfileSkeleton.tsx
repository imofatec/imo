export default function SocialProfileSkeleton() {
  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto w-full max-w-6xl px-4 py-8">
        <div className="overflow-hidden rounded-4xl border border-white/10 bg-[#14082f]">
          <div className="border-b border-white/10 px-6 py-6">
            <div className="h-10 w-44 rounded-2xl bg-white/8" />
          </div>
          <div className="grid gap-8 px-6 py-8 lg:grid-cols-[320px_minmax(0,1fr)]">
            <div>
              <div className="h-72 rounded-3xl bg-white/8" />
            </div>
            <div className="space-y-4 rounded-3xl bg-white/6 p-4">
              <div className="h-8 w-48 rounded-2xl bg-white/8" />
              <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                <div className="h-64 rounded-3xl bg-white/8" />
                <div className="h-64 rounded-3xl bg-white/8" />
                <div className="h-64 rounded-3xl bg-white/8" />
              </div>
            </div>
          </div>
          <div className="space-y-4 px-6 pb-8 lg:px-8">
            <div className="h-32 rounded-3xl bg-white/6" />
            <div className="space-y-4 rounded-3xl bg-white/6 p-4">
              <div className="h-8 w-40 rounded-2xl bg-white/8" />
              <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
                <div className="h-56 rounded-3xl bg-white/8" />
                <div className="h-56 rounded-3xl bg-white/8" />
                <div className="h-56 rounded-3xl bg-white/8" />
              </div>
              <div className="h-40 rounded-3xl bg-white/8" />
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}

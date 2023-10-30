require 'json'
dir = File.join File.expand_path(__dir__), 'src', 'main'
strdir = File.join dir, *%w"resources KappaModResources localization eng"

cardstrs = JSON.parse File.read File.join strdir, 'KappaMod-Card-Strings.json'
cards = Dir.glob(File.join dir, *%w"java com ascript KappaMod cards *.java").grep_v(/Abstract/).map{|f|f[/\b\w+(?=\.java$)/]}
cards.each do |f|
  id = 'KappaMod:' + f
  if !cardstrs[id]
    puts "Adding card " + f
    cardstrs[id] = {
      'NAME'        => f.gsub(/([a-z])([A-Z])/, '\1 \2'),
      'DESCRIPTION' => 'TODO!!!!!!!'
    }
  end
end
File.write File.join(strdir, 'KappaMod-Card-Strings-Fixed.json'), JSON.pretty_generate(cardstrs)

powstrs = JSON.parse File.read File.join strdir, 'KappaMod-Power-Strings.json'
pows = Dir.glob(File.join dir, *%w"java com ascript KappaMod powers *.java").grep_v(/Abstract/).map{|f|f[/\b\w+(?=\.java$)/]}
pows.each do |f|
  id = 'KappaMod:' + f
  if !powstrs[id]
    puts "Adding power " + f
    powstrs[id] = {
      'NAME'        => f.sub(/Power$/, '').gsub(/([a-z])([A-Z])/, '\1 \2'),
      'DESCRIPTIONS' => ['TODO!!!!!!!']
    }
  end
end
File.write File.join(strdir, 'KappaMod-Power-Strings-Fixed.json'), JSON.pretty_generate(powstrs)
